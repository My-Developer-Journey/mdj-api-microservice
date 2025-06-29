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
@Document(collection = "follow")
public class Follow {
    @Id
    private UUID id;
    private UUID followerId;
    private UUID followingId;
    private LocalDateTime createdAt;
}
