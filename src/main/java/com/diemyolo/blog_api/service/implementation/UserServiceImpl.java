package com.diemyolo.blog_api.service.implementation;

import com.diemyolo.blog_api.entity.User;
import com.diemyolo.blog_api.exception.CustomException;
import com.diemyolo.blog_api.model.response.like.LikeResponse;
import com.diemyolo.blog_api.model.response.user.UserResponse;
import com.diemyolo.blog_api.mongo.entity.Like;
import com.diemyolo.blog_api.service.AuthenticationService;
import com.diemyolo.blog_api.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;

    @Autowired
    private AuthenticationService authenticationService;

    public UserServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserResponse getCurrentUser() {
        try {
            User currentUser = authenticationService.findUserByJwt();
            return modelMapper.map(currentUser, UserResponse.class);
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
