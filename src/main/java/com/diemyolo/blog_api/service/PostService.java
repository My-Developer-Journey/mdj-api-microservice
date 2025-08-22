package com.diemyolo.blog_api.service;

import com.diemyolo.blog_api.entity.Enumberable.PostStatus;
import com.diemyolo.blog_api.model.request.post.PostRequest;
import com.diemyolo.blog_api.model.response.post.PostResponse;
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
}