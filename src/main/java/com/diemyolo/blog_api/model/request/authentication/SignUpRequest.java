package com.diemyolo.blog_api.model.request.authentication;

import com.diemyolo.blog_api.entity.Enumberable.Gender;
import com.diemyolo.blog_api.entity.Enumberable.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "The email is required.")
    @Pattern(
            regexp = "^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,4}$",
            message = "The email is invalid."
    )
    @Schema(example = "example@gmail.com")
    private String email;

    @NotBlank(message = "The password is required.")
    private String password;

    @NotBlank(message = "The confirmed password is required.")
    private String confirmedPassword;

    @NotBlank(message = "The username is required.")
    private String userName;

    @NotBlank(message = "The phone is required.")
    private String phoneNumber;

    @NotNull(message = "The gender is required.")
    private Gender gender;

    @NotNull(message = "The role is required.")
    private Role role;
}
