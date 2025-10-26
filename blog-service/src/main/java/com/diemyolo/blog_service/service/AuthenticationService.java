package com.diemyolo.blog_service.service;

import com.diemyolo.blog_service.entity.User;
import com.diemyolo.blog_service.model.request.authentication.SignInRequest;
import com.diemyolo.blog_service.model.request.authentication.SignUpRequest;
import com.diemyolo.blog_service.model.response.user.UserResponse;

public interface AuthenticationService {
    UserResponse signUp(SignUpRequest request);

    String signIn(SignInRequest request);

    User findUserByJwt();

    void verifyEmail(String email, String code);
}
