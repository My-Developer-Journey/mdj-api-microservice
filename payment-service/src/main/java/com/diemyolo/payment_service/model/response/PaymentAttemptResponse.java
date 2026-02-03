package com.diemyolo.payment_service.model.response;

import java.math.BigDecimal;
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
public class PaymentAttemptResponse {
  private UUID id;
  private UUID userId;
  private String provider;
  private BigDecimal amount;
  private String currency;
  private String status;
  private String providerTxnId;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
