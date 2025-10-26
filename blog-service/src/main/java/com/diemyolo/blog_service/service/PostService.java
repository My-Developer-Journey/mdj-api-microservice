package com.diemyolo.blog_service.service;

import com.diemyolo.blog_service.entity.Enumberable.PostStatus;
import com.diemyolo.blog_service.model.common.SuggestionResponse;
import com.diemyolo.blog_service.model.request.post.PostRequest;
import com.diemyolo.blog_service.model.response.post.PostResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface PostService {
    PostResponse addPost(PostRequest request, MultipartFile thumbnailFile);
    PostResponse checkDraftExist();
    PostResponse updatePostStatus(UUID postId, PostStatus status, @Nullable String rejectedNote);
    PostResponse updatePost(UUID postId, PostRequest request, MultipartFile thumbnailFile);
    PostResponse removePost(UUID postId);
    List<PostResponse> getUserPosts();
    List<PostResponse> getPostRequests();
    PostResponse getPostBySlug(String slug);
    SuggestionResponse suggestCategoriesAndTags(String title);
}