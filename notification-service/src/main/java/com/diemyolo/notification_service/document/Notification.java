package com.diemyolo.notification_service.document;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

  @Id private String id;

  private UUID userId;

  private NotificationType type;

  private Map<String, Object> payload;

  @Builder.Default private boolean read = false;

  @Builder.Default private LocalDateTime createdAt = LocalDateTime.now();

  public enum NotificationType {
    BLOG_LIKED,
    COMMENTED,
    PAYMENT_SUCCESS,
    PAYMENT_FAILED,
    NEW_FOLLOWER,
    BLOG_PUBLISHED
  }
}
