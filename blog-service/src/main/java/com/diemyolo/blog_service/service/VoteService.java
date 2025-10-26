package com.diemyolo.blog_service.service;

import com.diemyolo.blog_service.model.response.vote.VoteResponse;

import java.util.UUID;

public interface VoteService {
    VoteResponse upvotePost(UUID postId);

    VoteResponse downvotePost(UUID postId);
}