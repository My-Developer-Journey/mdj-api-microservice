package com.diemyolo.blog_service.service;

import com.diemyolo.blog_service.model.response.comment.CommentResponse;

import java.util.UUID;

public interface CommentService {
    CommentResponse commentOnPost(UUID postId, String content);

    void deleteComment(UUID commentId);
}
