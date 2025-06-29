package com.diemyolo.blog_api.model.response.follow;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class FollowResponse {
    private UUID id;
    private UUID followerId;
    private UUID followingId;
    private LocalDateTime createdAt;
}
