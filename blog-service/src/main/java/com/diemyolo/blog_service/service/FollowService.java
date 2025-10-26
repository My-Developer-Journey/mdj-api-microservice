package com.diemyolo.blog_service.service;

import com.diemyolo.blog_service.model.response.follow.FollowResponse;

import java.util.List;
import java.util.UUID;

public interface FollowService {
    FollowResponse followUser(UUID followingId);

    void unfollowUser(UUID followingId);

    List<FollowResponse> getFollowers();

    List<FollowResponse> getFollowing();
}
