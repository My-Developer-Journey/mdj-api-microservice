package com.diemyolo.blog_api.service;

import com.diemyolo.blog_api.entity.User;
import com.diemyolo.blog_api.model.request.authentication.SignInRequest;
import com.diemyolo.blog_api.model.request.authentication.SignUpRequest;
import com.diemyolo.blog_api.model.response.user.UserResponse;

public interface AuthenticationService {
    UserResponse signUp(SignUpRequest request);

    String signIn(SignInRequest request);

    User findUserByJwt();

    void verifyEmail(String email, String code);
}
