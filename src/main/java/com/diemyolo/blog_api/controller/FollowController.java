package com.diemyolo.blog_api.controller;

import com.diemyolo.blog_api.model.common.ApiResponse;
import com.diemyolo.blog_api.model.request.authentication.SignUpRequest;
import com.diemyolo.blog_api.model.response.follow.FollowResponse;
import com.diemyolo.blog_api.model.response.user.UserResponse;
import com.diemyolo.blog_api.service.AuthenticationService;
import com.diemyolo.blog_api.service.FollowService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/follows")
@RestController
public class FollowController {
    @Autowired
    private FollowService followService;

    @PostMapping("/{followingId}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<ApiResponse<FollowResponse>> followUser(@PathVariable UUID followingId) {
        FollowResponse followResponse = followService.followUser(followingId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.created(followResponse));
    }

    @DeleteMapping("/{followingId}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<ApiResponse<?>> unfollowUser(@PathVariable UUID followingId) {
        followService.unfollowUser(followingId);
        return ResponseEntity.ok(ApiResponse.success("Unfollow successfully!"));
    }

    @GetMapping("/followers")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<ApiResponse<List<FollowResponse>>> getFollowers() {
        List<FollowResponse> followers = followService.getFollowers();
        return ResponseEntity.ok(ApiResponse.success(followers));
    }

    @GetMapping("/following")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<ApiResponse<List<FollowResponse>>> getFollowing() {
        List<FollowResponse> following = followService.getFollowing();
        return ResponseEntity.ok(ApiResponse.success(following));
    }
}
