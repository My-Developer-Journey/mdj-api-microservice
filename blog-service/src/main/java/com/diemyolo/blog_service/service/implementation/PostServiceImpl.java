package com.diemyolo.blog_service.service.implementation;

import com.diemyolo.blog_service.entity.Category;
import com.diemyolo.blog_service.entity.Enumberable.PostStatus;
import com.diemyolo.blog_service.entity.Enumberable.Role;
import com.diemyolo.blog_service.entity.Post;
import com.diemyolo.blog_service.entity.Tag;
import com.diemyolo.blog_service.entity.User;
import com.diemyolo.blog_service.exception.CustomException;
import com.diemyolo.blog_service.model.common.SuggestionResponse;
import com.diemyolo.blog_service.model.request.post.PostRequest;
import com.diemyolo.blog_service.model.response.post.PostResponse;
import com.diemyolo.blog_service.repository.CategoryRepository;
import com.diemyolo.blog_service.repository.PostRepository;
import com.diemyolo.blog_service.repository.TagRepository;
import com.diemyolo.blog_service.repository.UserRepository;
import com.diemyolo.blog_service.service.AWSS3Service;
import com.diemyolo.blog_service.service.AuthenticationService;
import com.diemyolo.blog_service.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final ChatClient chatClient;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AWSS3Service awsS3Service;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, CategoryRepository categoryRepository, TagRepository tagRepository, ChatClient.Builder chatClientBuilder) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
        this.chatClient = chatClientBuilder.build();
    }

    // User function
    @Transactional
    @Override
    public PostResponse addPost(PostRequest request, MultipartFile thumbnailFile) {
        try {
            // Kiểm tra title đã tồn tại
            if (postRepository.existsByTitle(request.getTitle())) {
                throw new CustomException("Title already exists", HttpStatus.BAD_REQUEST);
            }

            // Tìm author và check quyền
            User author = userRepository.findById(request.getAuthorId())
                    .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
            User currentUser = authenticationService.findUserByJwt();
            if (!currentUser.getEmail().equals(author.getEmail())) {
                throw new CustomException("You are not authorized to update this user.", HttpStatus.FORBIDDEN);
            }

            // Validate categories
            List<Category> categories = validateIds(request.getCategoryIds(), categoryRepository, Category::getId, "Invalid category IDs");

            // Validate tags
            List<Tag> tags = validateIds(request.getTagIds(), tagRepository, Tag::getId, "Invalid tag IDs");

            // Generate slug & SEO
            String slug = generateSlug(request.getTitle());
            String seoTitle = generateSeoTitle(request.getTitle());
            String seoDescription = generateSeoDescription(request.getContent());
            String seoKeywords = generateSeoKeywords(request.getTitle(), tags);

            //Add thumbnail
            Map<String, String> result = awsS3Service.uploadFile(thumbnailFile);
            String url = result.get("url");
            String key = result.get("key");

            // Tạo entity Post
            Post post = Post.builder()
                    .title(request.getTitle())
                    .slug(slug)
                    .thumbnailUrl(url)
                    .thumbnailS3Key(key)
                    .content(request.getContent())
                    .contentJson(request.getContentJson())
                    .seoTitle(seoTitle)
                    .seoDescription(seoDescription)
                    .seoKeywords(seoKeywords)
                    .author(author)
                    .categories(categories)
                    .tags(tags)
                    .postStatus(request.getPostStatus())
                    .submittedAt(LocalDateTime.now())
                    .scheduledPublishDate(request.getScheduledPublishDate())
                    .build();

            post = postRepository.save(post);
            return convertToResponse(post);
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    @Override
    public PostResponse checkDraftExist() {
        try {
            User currentUser = authenticationService.findUserByJwt();

            Optional<Post> draftPost = postRepository.findFirstByAuthorIdAndPostStatus(currentUser.getId(), PostStatus.DRAFT);

            return draftPost.map(this::convertToResponse).orElse(null);
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public PostResponse updatePost(UUID postId, PostRequest request, MultipartFile thumbnailFile) {
        try {
            // Tìm post cần cập nhật
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new CustomException("Post not found", HttpStatus.NOT_FOUND));

            // Tìm user hiện tại
            User currentUser = authenticationService.findUserByJwt();

            // Chỉ author mới được chỉnh sửa
            if (!post.getAuthor().getEmail().equals(currentUser.getEmail())) {
                throw new CustomException("You are not authorized to update this post.", HttpStatus.FORBIDDEN);
            }

            if (!post.getTitle().equals(request.getTitle()) &&
                    postRepository.existsByTitle(request.getTitle())) {
                throw new CustomException("Title already exists", HttpStatus.BAD_REQUEST);
            }

            //Add thumbnail
            if(thumbnailFile != null && !thumbnailFile.isEmpty()){
                Map<String, String> result = awsS3Service.uploadFile(thumbnailFile);
                String url = result.get("url");
                String key = result.get("key");
                post.setThumbnailUrl(url);
                post.setThumbnailS3Key(key);
            }

            // Cập nhật các trường cơ bản
            post.setTitle(request.getTitle());
            post.setContent(request.getContent());
            post.setContentJson(request.getContentJson());
            post.setUpdatedDate(LocalDateTime.now());
            post.setPostStatus(request.getPostStatus());

            // Cập nhật category nếu có
            List<Category> categories = validateIds(request.getCategoryIds(), categoryRepository, Category::getId, "Invalid category IDs");
            if(!categories.isEmpty()){
                post.setCategories(categories);
            }

            // Cập nhật tags nếu có
            List<Tag> tags = validateIds(request.getTagIds(), tagRepository, Tag::getId, "Invalid tag IDs");
            if(!tags.isEmpty()){
                post.setTags(tags);
            }

            if(post.getPostStatus() == PostStatus.DRAFT){
                // Generate slug & SEO
                String slug = generateSlug(request.getTitle());
                String seoTitle = generateSeoTitle(request.getTitle());
                String seoDescription = generateSeoDescription(request.getContent());
                String seoKeywords = generateSeoKeywords(request.getTitle(), tags);
                //set slug, seo mới
                post.setSlug(slug);
                post.setSeoTitle(seoTitle);
                post.setSeoDescription(seoDescription);
                post.setSeoKeywords(seoKeywords);
            }

            postRepository.save(post);

            return convertToResponse(post);

        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public PostResponse removePost(UUID postId) {
        try {
            User currentUser = authenticationService.findUserByJwt();

            // Tìm post
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new CustomException("Post not found", HttpStatus.NOT_FOUND));

            if (!currentUser.getEmail().equals(post.getAuthor().getEmail())) {
                throw new CustomException("You are not authorized to perform this action.", HttpStatus.FORBIDDEN);
            }

            // Cập nhật status
            post.setPostStatus(PostStatus.REMOVED);

            postRepository.save(post);

            return convertToResponse(post);
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public List<PostResponse> getUserPosts() {
        try {
            User currentUser = authenticationService.findUserByJwt();

            List<Post> posts = postRepository.findByAuthorId(
                    currentUser.getId(),
                    Sort.by(Sort.Direction.DESC, "updatedDate")
            );

            return posts.stream()
                    .map(this::convertToResponse)
                    .toList();
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public PostResponse getPostBySlug(String slug){
        try {
            Post post = postRepository.findBySlug(slug).orElseThrow(() -> new CustomException("Post not found", HttpStatus.NOT_FOUND));
            return convertToResponse(post);
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    // Admin function
    @Override
    public List<PostResponse> getPostRequests() {
        try {
            // Kiểm tra quyền admin
            User currentUser = authenticationService.findUserByJwt();
            if (!currentUser.getRole().equals(Role.ADMIN)) {
                throw new CustomException("You are not authorized to perform this action.", HttpStatus.FORBIDDEN);
            }

            List<Post> posts = postRepository.findByPostStatus(PostStatus.SUBMITTED);

            return posts.stream()
                    .map(this::convertToResponse)
                    .toList();
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public PostResponse updatePostStatus(UUID postId, PostStatus status, @Nullable String rejectedNote) {
        try {
            // Kiểm tra quyền admin
            User currentUser = authenticationService.findUserByJwt();
            if (!currentUser.getRole().equals(Role.ADMIN)) {
                throw new CustomException("You are not authorized to perform this action.", HttpStatus.FORBIDDEN);
            }

            // Tìm post
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new CustomException("Post not found", HttpStatus.NOT_FOUND));

            // Cập nhật status và các thời điểm liên quan
            post.setPostStatus(status);

            switch (status) {
                case REJECTED -> {
                    post.setRejectedAt(LocalDateTime.now());
                    post.setRejectedNote(rejectedNote);
                }
                case ACCEPTED -> {
                    post.setAcceptedAt(LocalDateTime.now());
                }
                case DRAFT -> {
                    post.setRejectedAt(null);
                    post.setRejectedNote(null);
                }
                case SUBMITTED -> {
                    post.setSubmittedAt(LocalDateTime.now());
                }
            }

            postRepository.save(post);

            return convertToResponse(post);
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public SuggestionResponse suggestCategoriesAndTags(String title) {
        try {
            // Lấy danh sách categories và tags từ DB
            List<String> categories = categoryRepository.findAll()
                    .stream()
                    .map(Category::getName)
                    .toList();

            List<String> tags = tagRepository.findAll()
                    .stream()
                    .map(Tag::getName)
                    .toList();

            // Tạo system prompt (yêu cầu AI trả JSON chuẩn)
            final String systemTemplate = """
            Here is the list of available categories: %s
            Here is the list of available tags: %s

            Task:
            - Suggest 1–5 categories and tags from the above lists for the given title: "%s".
            - Only choose from the given lists, do not invent new ones.
            - If nothing matches, reply with:
              {"categories": [], "tags": [], "message": "We currently don't have tags/category corresponding to the title in the database."}
            - If the input is not a blog title, reply with:
              {"categories": [], "tags": [], "message": "We only support finding suitable categories and tags, any questions outside cannot be answered."}
            - Otherwise, reply strictly in JSON format:
              {"categories": ["..."], "tags": ["..."]}
            """;

            String systemMessage = systemTemplate.formatted(categories, tags, title);

            String aiResponse = this.chatClient.prompt()
                    .system(c -> c.text(systemMessage))
                    .user(title)
                    .call()
                    .content();

            // Parse JSON về object
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(aiResponse, SuggestionResponse.class);

        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    // Other validation functions
    private <T> List<T> validateIds(List<UUID> ids, JpaRepository<T, UUID> repository, Function<T, UUID> getIdFunc, String errorMessage) {
        try {
            if (ids == null || ids.isEmpty()) return new ArrayList<>();

            List<T> entities = repository.findAllById(ids);
            if (entities.size() != ids.size()) {
                List<UUID> foundIds = entities.stream().map(getIdFunc).toList();
                List<UUID> missingIds = ids.stream().filter(id -> !foundIds.contains(id)).toList();
                throw new CustomException(errorMessage + ": " + missingIds, HttpStatus.BAD_REQUEST);
            }
            return entities;
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public PostResponse convertToResponse(Post post) {
        return modelMapper.map(post, PostResponse.class);
    }

    private String generateSlug(String title) {
        return title.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "") // bỏ ký tự đặc biệt
                .replaceAll("\\s+", "-")         // thay khoảng trắng thành dấu gạch ngang
                .replaceAll("-{2,}", "-")        // bỏ dấu gạch ngang thừa
                .replaceAll("^-|-$", "");        // bỏ gạch ngang đầu/cuối
    }

    private String generateSeoTitle(String title) {
        return title.length() > 60 ? title.substring(0, 60) : title;
    }

    private String generateSeoDescription(String content) {
        String plainText = content.replaceAll("\\<.*?\\>", "");
        return plainText.length() > 160 ? plainText.substring(0, 160) : plainText;
    }

    private String generateSeoKeywords(String title, List<Tag> tags) {
        List<String> keywords = new ArrayList<>();
        keywords.addAll(Arrays.asList(title.toLowerCase().split("\\s+")));
        if (tags != null) {
            keywords.addAll(tags.stream()
                    .map(t -> t.getName().toLowerCase())
                    .toList());
        }
        return String.join(", ", keywords);
    }
}