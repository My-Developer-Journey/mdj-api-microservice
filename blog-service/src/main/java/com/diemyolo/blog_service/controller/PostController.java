package com.diemyolo.blog_service.controller;

import com.diemyolo.blog_service.entity.Enumberable.PostStatus;
import com.diemyolo.blog_service.model.common.ApiResponse;
import com.diemyolo.blog_service.model.common.SuggestionResponse;
import com.diemyolo.blog_service.model.request.post.PostRequest;
import com.diemyolo.blog_service.model.response.post.PostResponse;
import com.diemyolo.blog_service.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/posts")
@RestController
public class PostController {
    @Autowired
    private PostService postService;

    // User function
    @PostMapping()
    public ResponseEntity<ApiResponse<Object>> addPost(
            @Valid @RequestPart PostRequest request, @RequestParam("file") MultipartFile thumbnailFile
    ) {
        PostResponse response = postService.addPost(request, thumbnailFile);

        String message;
        if (response.getPostStatus() == PostStatus.DRAFT) {
            message = "Save draft successfully!";
        } else if (response.getPostStatus() == PostStatus.SUBMITTED) {
            message = "Add post successfully, please wait for admin verification!";
        } else {
            message = "Post added successfully!";
        }

        return ResponseEntity.ok(ApiResponse.success(message, response));
    }

    @GetMapping("/draft")
    public ResponseEntity<ApiResponse<Object>> checkDraftExist() {
        PostResponse response = postService.checkDraftExist();

        String message;
        if (response == null) {
            message = "No draft existed!";
        } else if (response.getPostStatus() == PostStatus.SUBMITTED) {
            message = "Draft found!";
        } else {
            message = "No draft existed!";
        }

        return ResponseEntity.ok(ApiResponse.success(message, response));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<ApiResponse<Object>> updatePost(
            @PathVariable UUID postId,
            @Valid @RequestPart PostRequest request, @RequestParam(value = "file", required = false) MultipartFile thumbnailFile
    ) {
        PostResponse response = postService.updatePost(postId, request, thumbnailFile);
        return ResponseEntity.ok(ApiResponse.success("Post updated successfully", response));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Object>> removePost(
            @PathVariable UUID postId
    ) {
        PostResponse response = postService.removePost(postId);
        return ResponseEntity.ok(ApiResponse.success("Post removed successfully", response));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<PostResponse>>> getUserPosts() {
        List<PostResponse> response = postService.getUserPosts();

        return ResponseEntity.ok(ApiResponse.success("User post fetched!", response));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<PostResponse>> getPostBySlug(@PathVariable String slug) {
        PostResponse response = postService.getPostBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success("Post fetched!", response));
    }

    // Admin function
    @PutMapping("/{postId}/status")
    public ResponseEntity<ApiResponse<Object>> updatePostStatus(
            @PathVariable UUID postId,
            @RequestParam PostStatus status,
            @RequestParam(required = false) String rejectedNote
    ) {
        PostResponse response = postService.updatePostStatus(postId, status, rejectedNote);
        return ResponseEntity.ok(ApiResponse.success("Post status updated successfully", response));
    }

    @PutMapping("/requests")
    public ResponseEntity<ApiResponse<List<PostResponse>>> getPostRequests() {
        List<PostResponse> response = postService.getPostRequests();

        return ResponseEntity.ok(ApiResponse.success("Post request fetched!", response));
    }

    // AI function
    @GetMapping("/recommendation")
    public ResponseEntity<ApiResponse<SuggestionResponse>> suggestCategoriesAndTags(@RequestParam(name = "title", required = true) String title) {
        SuggestionResponse result = postService.suggestCategoriesAndTags(title);
        return ResponseEntity.ok(ApiResponse.success("Suggested successfully", result));
    }
}