package com.diemyolo.user_service.service;

import com.diemyolo.user_service.model.request.user.UserRequest;
import com.diemyolo.user_service.model.response.user.UserResponse;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
  UserResponse getCurrentUser();

  UserResponse updateUser(UserRequest request);

  UserResponse updateUserStatus(UUID userId);

  UserResponse getUserById(UUID userId);

  Page<UserResponse> getAllUsers(int page, int size, String sortBy, String sortDir);

  UserResponse updateUserAvatar(MultipartFile file, String userEmail);
}
