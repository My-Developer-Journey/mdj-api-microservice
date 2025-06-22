package com.diemyolo.blog_api.controller;

import java.io.UnsupportedEncodingException;

import com.diemyolo.blog_api.exception.CustomException;
import com.diemyolo.blog_api.model.request.authentication.SignInRequest;
import com.diemyolo.blog_api.model.request.authentication.SignUpRequest;
import com.diemyolo.blog_api.model.response.authentication.AuthenticationResponse;
import com.diemyolo.blog_api.model.response.user.UserResponse;
import com.diemyolo.blog_api.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.diemyolo.blog_api.model.common.ApiResponse;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

@RequestMapping("/api/authentications")
@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("sign-up")
    public ResponseEntity<ApiResponse<UserResponse>> register(@Valid @RequestBody SignUpRequest request) {
        UserResponse user = authenticationService.signUp(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.created(user));
    }

    @PostMapping("sign-in")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> login(@Valid @RequestBody SignInRequest request)
            throws UnsupportedEncodingException, MessagingException {
        AuthenticationResponse response = authenticationService.signIn(request);
        return ResponseEntity
                .ok(ApiResponse.success("Login successful", response));
    }
}