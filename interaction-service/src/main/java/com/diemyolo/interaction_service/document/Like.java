package com.diemyolo.interaction_service.document;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "likes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Like {

  @Id private String id;

  private UUID userId;

  private TargetType targetType;

  private UUID targetId;

  @Builder.Default private LocalDateTime createdAt = LocalDateTime.now();

  public enum TargetType {
    BLOG,
    COMMENT
  }
}
