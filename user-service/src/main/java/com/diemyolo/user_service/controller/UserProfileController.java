package com.diemyolo.user_service.controller;

import com.diemyolo.user_service.entity.UserProfile;
import com.diemyolo.user_service.model.request.UserProfileRequest;
import com.diemyolo.user_service.model.response.UserProfileResponse;
import com.diemyolo.user_service.service.UserProfileService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-profiles")
@RequiredArgsConstructor
public class UserProfileController {

  private final UserProfileService service;

  @PostMapping
  public ResponseEntity<UserProfileResponse> create(@RequestBody UserProfileRequest request) {
    // Implementation will be handled later
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/{userId}")
  public ResponseEntity<UserProfileResponse> getByUserId(@PathVariable UUID userId) {
    return service
        .findByUserId(userId)
        .map(this::toResponse)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<UserProfileResponse>> getAll() {
    List<UserProfileResponse> responses = service.findAll().stream().map(this::toResponse).toList();
    return ResponseEntity.ok(responses);
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<Void> deleteByUserId(@PathVariable UUID userId) {
    service.deleteById(userId);
    return ResponseEntity.noContent().build();
  }

  private UserProfileResponse toResponse(UserProfile entity) {
    return UserProfileResponse.builder()
        .userId(entity.getUserId())
        .fullName(entity.getFullName())
        .avatarUrl(entity.getAvatarUrl())
        .bio(entity.getBio())
        .build();
  }
}
