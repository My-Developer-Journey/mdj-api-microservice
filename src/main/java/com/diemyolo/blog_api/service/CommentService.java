package com.diemyolo.blog_api.service;

import com.diemyolo.blog_api.model.response.comment.CommentResponse;

import java.util.UUID;

public interface CommentService {
    CommentResponse commentOnPost(UUID postId, String content);

    void deleteComment(UUID commentId);
}
