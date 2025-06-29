package com.diemyolo.blog_api.model.response.like;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class LikeResponse {
    private UUID id;
    private UUID postId;
    private UUID userId;
    private LocalDateTime createdAt;
}
