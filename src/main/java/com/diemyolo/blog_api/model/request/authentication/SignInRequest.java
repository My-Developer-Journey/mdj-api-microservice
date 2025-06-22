package com.diemyolo.blog_api.model.request.authentication;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest {
    @NotBlank(message = "The email is required.")
    private String email;

    @NotBlank(message = "The password is required.")
    private String password;
}
