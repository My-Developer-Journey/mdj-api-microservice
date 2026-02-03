package com.diemyolo.payment_service.model.response;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionResponse {
  private UUID id;
  private UUID userId;
  private String plan;
  private String status;
  private LocalDateTime startedAt;
  private LocalDateTime expiredAt;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
