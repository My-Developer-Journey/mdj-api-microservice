package com.diemyolo.blog_api.service;

import com.diemyolo.blog_api.model.request.user.UserRequest;
import com.diemyolo.blog_api.model.response.user.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UserService {
    UserResponse getCurrentUser();

    UserResponse updateUser(UserRequest request);

    UserResponse updateUserStatus(UUID userId);

    UserResponse getUserById(UUID userId);

    Page<UserResponse> getAllUsers(int page, int size, String sortBy, String sortDir);

    UserResponse updateUserAvatar(MultipartFile file, String userEmail);
}