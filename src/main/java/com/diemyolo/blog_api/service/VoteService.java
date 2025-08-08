package com.diemyolo.blog_api.service;

import com.diemyolo.blog_api.model.response.vote.VoteResponse;

import java.util.UUID;

public interface VoteService {
    VoteResponse upvotePost(UUID postId);

    VoteResponse downvotePost(UUID postId);
}