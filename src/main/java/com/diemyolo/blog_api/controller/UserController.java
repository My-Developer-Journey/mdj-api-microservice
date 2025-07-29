package com.diemyolo.blog_api.controller;

import com.diemyolo.blog_api.model.common.ApiResponse;
import com.diemyolo.blog_api.model.request.user.UserRequest;
import com.diemyolo.blog_api.model.response.user.UserResponse;
import com.diemyolo.blog_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RequestMapping("/api/users")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("profile")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser() {
        UserResponse user = userService.getCurrentUser();
        return ResponseEntity
                .ok(ApiResponse.success("User retrieved!", user));
    }

    @PutMapping("profile")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@Valid @RequestBody UserRequest request) {
        UserResponse user = userService.updateUser(request);
        return ResponseEntity.ok(
                ApiResponse.success("User updated successfully", user)
        );
    }

    @PutMapping("/{userId}/status")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserStatus(@PathVariable UUID userId) {
        UserResponse user = userService.updateUserStatus(userId);
        return ResponseEntity.ok(
                ApiResponse.success("User status updated successfully", user)
        );
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<Page<UserResponse>>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Page<UserResponse> users = userService.getAllUsers(page, size, sortBy, sortDir);
        return ResponseEntity.ok(ApiResponse.success("Get all users successfully", users));
    }

    @PutMapping("profile/avatar")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserAvatar(@RequestParam("file") MultipartFile file, @RequestParam("email") String email) {
        UserResponse user = userService.updateUserAvatar(file, email);
        return ResponseEntity.ok(
                ApiResponse.success("User avatar updated successfully", user)
        );
    }
}