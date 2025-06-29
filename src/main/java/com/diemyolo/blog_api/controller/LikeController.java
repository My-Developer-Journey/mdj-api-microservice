package com.diemyolo.blog_api.controller;

import com.diemyolo.blog_api.model.common.ApiResponse;
import com.diemyolo.blog_api.model.response.like.LikeResponse;
import com.diemyolo.blog_api.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/likes")
@RestController
public class LikeController {
    @Autowired
    private LikeService likeService;

    @PostMapping("/{postId}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<ApiResponse<LikeResponse>> likePost(@PathVariable UUID postId) {
        LikeResponse likeResponse = likeService.likePost(postId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.created(likeResponse));
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<ApiResponse<?>> unlikePost(@PathVariable UUID postId) {
        likeService.unlikePost(postId);
        return ResponseEntity.ok(ApiResponse.success("Unlike post successfully!"));
    }
}
