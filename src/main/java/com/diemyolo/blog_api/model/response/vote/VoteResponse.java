package com.diemyolo.blog_api.model.response.vote;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class VoteResponse {
    private UUID id;
    private UUID postId;
    private UUID userId;
    private LocalDateTime createdAt;
}
