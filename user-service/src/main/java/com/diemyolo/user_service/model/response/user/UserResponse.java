package com.diemyolo.user_service.model.response.user;

import com.diemyolo.user_service.entity.enumberable.Gender;
import com.diemyolo.user_service.entity.enumberable.Role;
import com.diemyolo.user_service.entity.enumberable.Status;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
  private UUID id;
  private LocalDateTime createdDate;
  private String email;
  private String displayName;
  private String phoneNumber;
  private @Nullable String avatar;
  private @Nullable String avatarS3Key;
  private @Nullable String bio;
  private @Nullable String facebookUrl;
  private @Nullable String githubUrl;
  private Gender gender;
  private Status status;
  private Role role;
}
