package com.diemyolo.auth_service.model.response;

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
public class RefreshTokenResponse {
  private UUID id;
  private UUID userId;
  private String token;
  private LocalDateTime expiredAt;
  private boolean revoked;
  private LocalDateTime createdAt;
}
