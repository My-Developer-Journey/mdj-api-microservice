package com.diemyolo.user_service.model.response;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileResponse {
  private UUID userId;
  private String fullName;
  private String avatarUrl;
  private String bio;
}
