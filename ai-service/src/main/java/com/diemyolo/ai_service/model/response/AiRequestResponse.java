package com.diemyolo.ai_service.model.response;

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
public class AiRequestResponse {
  private UUID id;
  private UUID userId;
  private String prompt;
  private String response;
  private String model;
  private Integer tokensUsed;
  private LocalDateTime createdAt;
}
