package com.diemyolo.blog_api.mongo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "vote")
public class Vote {
    @Id
    private UUID id;
    private UUID postId;
    private UUID userId;
    private boolean isUpvote;
    private boolean isDownvote;
    private LocalDateTime createdAt;
}