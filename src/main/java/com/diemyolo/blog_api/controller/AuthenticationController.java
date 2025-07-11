package com.diemyolo.blog_api.controller;

import java.io.UnsupportedEncodingException;

import com.diemyolo.blog_api.model.request.authentication.SignInRequest;
import com.diemyolo.blog_api.model.request.authentication.SignUpRequest;
import com.diemyolo.blog_api.model.response.authentication.AuthenticationResponse;
import com.diemyolo.blog_api.model.response.user.UserResponse;
import com.diemyolo.blog_api.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.diemyolo.blog_api.model.common.ApiResponse;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

@RequestMapping("/api/authentications")
@RestController
public class AuthenticationController {

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("sign-up")
    public ResponseEntity<ApiResponse<UserResponse>> register(@Valid @RequestBody SignUpRequest request) {
        UserResponse user = authenticationService.signUp(request);
        return ResponseEntity
                .ok(ApiResponse.success("Register successfully. Please open your email to verify your account!", user));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse<Object>> login(
            @Valid @RequestBody SignInRequest request,
            HttpServletResponse servletResponse
    ) {
        String token = authenticationService.signIn(request);

        // ✅ Set token vào cookie
        Cookie cookie = new Cookie("accessToken", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge((int) (jwtExpiration / 1000));
        servletResponse.addCookie(cookie);
        
        return ResponseEntity.ok(ApiResponse.success("Login successful", null));
    }

    @GetMapping("verify")
    public ResponseEntity<?> verifyEmail(@RequestParam String email, @RequestParam String code) {
        authenticationService.verifyEmail(email, code);
        return ResponseEntity.ok("Verify successfully, please login to your account.");
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Object>> logout(HttpServletResponse response) {
        // Viết đè cookie cũ bằng cookie mới có maxAge = 0;
        Cookie cookie = new Cookie("accessToken", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(0);

        response.addCookie(cookie);

        return ResponseEntity.ok(ApiResponse.success("Logout successful", null));
    }
}