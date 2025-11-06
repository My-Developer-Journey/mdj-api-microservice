package com.diemyolo.user_service.service.implementation;

import com.diemyolo.user_service.model.request.user.UserRequest;
import com.diemyolo.user_service.model.response.user.UserResponse;
import com.diemyolo.user_service.repository.UserRepository;
import com.diemyolo.user_service.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserResponse getCurrentUser() {
        return null;
    }

    @Override
    public UserResponse updateUser(UserRequest request) {
        return null;
    }

    @Override
    public UserResponse updateUserStatus(UUID userId) {
        return null;
    }

    @Override
    public UserResponse getUserById(UUID userId) {
        return null;
    }

    @Override
    public Page<UserResponse> getAllUsers(int page, int size, String sortBy, String sortDir) {
        return null;
    }

    @Override
    public UserResponse updateUserAvatar(MultipartFile file, String userEmail) {
        return null;
    }
}