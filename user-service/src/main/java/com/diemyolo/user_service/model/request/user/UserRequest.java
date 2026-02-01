package com.diemyolo.user_service.model.request.user;

import com.diemyolo.user_service.entity.enumberable.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
  @NotBlank(message = "The email is required.")
  private String email;

  @NotBlank(message = "The username is required.")
  private String displayName;

  @NotBlank(message = "The phone is required.")
  private String phoneNumber;

  private @Nullable String bio;

  private @Nullable String facebookUrl;

  private @Nullable String githubUrl;

  @NotNull(message = "The gender is required.")
  private Gender gender;
}
