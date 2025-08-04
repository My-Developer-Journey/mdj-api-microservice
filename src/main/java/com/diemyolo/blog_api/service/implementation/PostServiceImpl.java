package com.diemyolo.blog_api.service.implementation;

import com.diemyolo.blog_api.entity.Category;
import com.diemyolo.blog_api.entity.Enumberable.PostStatus;
import com.diemyolo.blog_api.entity.Enumberable.Role;
import com.diemyolo.blog_api.entity.Post;
import com.diemyolo.blog_api.entity.User;
import com.diemyolo.blog_api.exception.CustomException;
import com.diemyolo.blog_api.model.request.post.PostRequest;
import com.diemyolo.blog_api.model.response.post.PostCategoryResponse;
import com.diemyolo.blog_api.model.response.post.PostResponse;
import com.diemyolo.blog_api.model.response.post.PostUserResponse;
import com.diemyolo.blog_api.repository.CategoryRepository;
import com.diemyolo.blog_api.repository.PostRepository;
import com.diemyolo.blog_api.repository.UserRepository;
import com.diemyolo.blog_api.service.AuthenticationService;
import com.diemyolo.blog_api.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public PostResponse addPost(PostRequest request) {
        try {
            // Kiểm tra slug đã tồn tại chưa
            if (postRepository.existsBySlug(request.getSlug())) {
                throw new CustomException("Slug already exists", HttpStatus.BAD_REQUEST);
            }

            // Kiểm tra title đã tồn tại chưa
            if (postRepository.existsByTitle(request.getTitle())) {
                throw new CustomException("Title already exists", HttpStatus.BAD_REQUEST);
            }

            // Tìm author
            User author = userRepository.findById(request.getAuthorId())
                    .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

            User currentUser = authenticationService.findUserByJwt();
            if (!currentUser.getEmail().equals(author.getEmail())) {
                throw new CustomException("You are not authorized to update this user.", HttpStatus.FORBIDDEN);
            }

            // Kiểm tra categoryIds hợp lệ
            List<UUID> categoryIds = request.getCategoryIds();
            List<Category> categories = new ArrayList<>();

            if (categoryIds != null && !categoryIds.isEmpty()) {
                categories = categoryRepository.findAllById(categoryIds);

                if (categories.size() != categoryIds.size()) {
                    List<UUID> foundIds = categories.stream()
                            .map(Category::getId)
                            .toList();
                    List<UUID> missingIds = categoryIds.stream()
                            .filter(id -> !foundIds.contains(id))
                            .toList();

                    throw new CustomException("Invalid category IDs: " + missingIds, HttpStatus.BAD_REQUEST);
                }
            }

            // Tạo entity Post
            Post post = Post.builder()
                    .title(request.getTitle())
                    .slug(request.getSlug())
                    .content(request.getContent())
                    .seoTitle(request.getSeoTitle())
                    .seoDescription(request.getSeoDescription())
                    .seoKeywords(request.getSeoKeywords())
                    .author(author)
                    .categories(categories)
                    .postStatus(request.getPostStatus())
                    .submittedAt(LocalDateTime.now())
                    .build();

            post = postRepository.save(post);

            return convertToResponse(post);
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Transactional
    public PostResponse updatePostStatus(UUID postId, PostStatus status, @Nullable String rejectedNote) {
        try {
            // Kiểm tra quyền admin nếu cần (tuỳ vào hệ thống bạn có phân quyền hay không)
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
                case PUBLISHED -> {
                    post.setPublishedAt(LocalDateTime.now());
                    post.setRejectedAt(null);
                    post.setRejectedNote(null);
                }
                case REJECTED -> {
                    post.setRejectedAt(LocalDateTime.now());
                    post.setRejectedNote(rejectedNote);
                    post.setPublishedAt(null);
                }
                case DRAFT -> {
                    post.setRejectedAt(null);
                    post.setRejectedNote(null);
                    post.setPublishedAt(null);
                }
                case SUBMITTED -> {
                    post.setSubmittedAt(LocalDateTime.now());
                    post.setPublishedAt(null);
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

    @Transactional
    public PostResponse updatePost(UUID postId, PostRequest request) {
        try {
            // Tìm post cần cập nhật
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new CustomException("Post not found", HttpStatus.NOT_FOUND));

            // Tìm user hiện tại
            User currentUser = authenticationService.findUserByJwt();

            // Chỉ author hoặc admin mới được chỉnh sửa
            if (!post.getAuthor().getEmail().equals(currentUser.getEmail())) {
                throw new CustomException("You are not authorized to update this post.", HttpStatus.FORBIDDEN);
            }

            // Kiểm tra nếu slug hoặc title bị trùng (trừ chính nó)
            if (!post.getSlug().equals(request.getSlug()) &&
                    postRepository.existsBySlug(request.getSlug())) {
                throw new CustomException("Slug already exists", HttpStatus.BAD_REQUEST);
            }

            if (!post.getTitle().equals(request.getTitle()) &&
                    postRepository.existsByTitle(request.getTitle())) {
                throw new CustomException("Title already exists", HttpStatus.BAD_REQUEST);
            }

            // Cập nhật các trường cơ bản
            post.setTitle(request.getTitle());
            post.setSlug(request.getSlug());
            post.setContent(request.getContent());
            post.setSeoTitle(request.getSeoTitle());
            post.setSeoDescription(request.getSeoDescription());
            post.setSeoKeywords(request.getSeoKeywords());
            post.setPostStatus(request.getPostStatus());

            // Cập nhật category nếu có
            List<UUID> categoryIds = request.getCategoryIds();
            if (categoryIds != null && !categoryIds.isEmpty()) {
                List<Category> categories = categoryRepository.findAllById(categoryIds);

                if (categories.size() != categoryIds.size()) {
                    List<UUID> foundIds = categories.stream().map(Category::getId).toList();
                    List<UUID> missingIds = categoryIds.stream()
                            .filter(id -> !foundIds.contains(id))
                            .toList();
                    throw new CustomException("Invalid category IDs: " + missingIds, HttpStatus.BAD_REQUEST);
                }

                post.setCategories(categories);
            }

            postRepository.save(post);

            return convertToResponse(post);

        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public PostResponse convertToResponse(Post post) {
        return modelMapper.map(post, PostResponse.class);
    }
}