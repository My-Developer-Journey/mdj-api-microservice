package com.diemyolo.blog_api.model.request.user;

import com.diemyolo.blog_api.entity.Enumberable.Gender;
import com.diemyolo.blog_api.entity.Enumberable.Role;
import com.diemyolo.blog_api.entity.Enumberable.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    private String userName;

    @NotBlank(message = "The phone is required.")
    private String phoneNumber;
    private @Nullable String avatar;

    private @Nullable String bio;

    private @Nullable String facebookUrl;

    private @Nullable String githubUrl;

    @NotNull(message = "The gender is required.")
    private Gender gender;
}
