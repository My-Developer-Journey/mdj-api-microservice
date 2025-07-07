package com.diemyolo.blog_api.service;

import com.diemyolo.blog_api.entity.User;
import com.diemyolo.blog_api.model.request.authentication.SignInRequest;
import com.diemyolo.blog_api.model.request.authentication.SignUpRequest;
import com.diemyolo.blog_api.model.response.authentication.AuthenticationResponse;
import com.diemyolo.blog_api.model.response.user.UserResponse;

public interface AuthenticationService {
    UserResponse signUp(SignUpRequest request);

    AuthenticationResponse signIn(SignInRequest request);

    User findUserByJwt();

    void verifyEmail(String email, String code);
}
