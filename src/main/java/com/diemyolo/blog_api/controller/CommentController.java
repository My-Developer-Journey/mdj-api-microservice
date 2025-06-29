package com.diemyolo.blog_api.controller;

import com.diemyolo.blog_api.model.common.ApiResponse;
import com.diemyolo.blog_api.model.response.comment.CommentResponse;
import com.diemyolo.blog_api.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/comments")
@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/{postId}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<ApiResponse<CommentResponse>> commentOnPost(@PathVariable UUID postId, String content) {
        CommentResponse commentResponse = commentService.commentOnPost(postId, content);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.created(commentResponse));
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<?>> deleteComment(@PathVariable UUID postId) {
        commentService.deleteComment(postId);
        return ResponseEntity.ok(ApiResponse.success("Delete comment successfully!"));
    }
}
