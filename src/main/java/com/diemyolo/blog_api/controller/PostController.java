package com.diemyolo.blog_api.controller;

import com.diemyolo.blog_api.entity.Enumberable.PostStatus;
import com.diemyolo.blog_api.model.common.ApiResponse;
import com.diemyolo.blog_api.model.request.post.PostRequest;
import com.diemyolo.blog_api.model.response.post.PostResponse;
import com.diemyolo.blog_api.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RequestMapping("/api/posts")
@RestController
public class PostController {
    @Autowired
    private PostService postService;

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

    @PutMapping("/{postId}/status")
    public ResponseEntity<ApiResponse<Object>> updatePostStatus(
            @PathVariable UUID postId,
            @RequestParam PostStatus status,
            @RequestParam(required = false) String rejectedNote
    ) {
        PostResponse response = postService.updatePostStatus(postId, status, rejectedNote);
        return ResponseEntity.ok(ApiResponse.success("Post status updated successfully", response));
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
}
