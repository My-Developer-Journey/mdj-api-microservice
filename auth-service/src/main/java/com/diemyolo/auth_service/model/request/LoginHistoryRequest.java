package com.diemyolo.auth_service.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginHistoryRequest {
  private String ipAddress;
  private String userAgent;
}
