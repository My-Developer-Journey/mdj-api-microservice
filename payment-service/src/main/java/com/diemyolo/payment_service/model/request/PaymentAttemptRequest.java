package com.diemyolo.payment_service.model.request;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentAttemptRequest {
  private String provider;
  private BigDecimal amount;
  private String currency;
}
