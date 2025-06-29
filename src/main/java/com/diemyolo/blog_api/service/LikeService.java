package com.diemyolo.blog_api.service;

import com.diemyolo.blog_api.model.response.like.LikeResponse;

import java.util.UUID;

public interface LikeService {
    LikeResponse likePost(UUID postId);

    void unlikePost(UUID postId);
}
