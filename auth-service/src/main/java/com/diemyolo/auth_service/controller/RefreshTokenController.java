package com.diemyolo.auth_service.controller;

import com.diemyolo.auth_service.entity.RefreshToken;
import com.diemyolo.auth_service.model.request.RefreshTokenRequest;
import com.diemyolo.auth_service.model.response.RefreshTokenResponse;
import com.diemyolo.auth_service.service.RefreshTokenService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/refresh-tokens")
@RequiredArgsConstructor
public class RefreshTokenController {

  private final RefreshTokenService service;

  @PostMapping
  public ResponseEntity<RefreshTokenResponse> create(@RequestBody RefreshTokenRequest request) {
    // Implementation will be handled later
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<RefreshTokenResponse> getById(@PathVariable UUID id) {
    return service
        .findById(id)
        .map(this::toResponse)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<RefreshTokenResponse>> getAll() {
    List<RefreshTokenResponse> responses =
        service.findAll().stream().map(this::toResponse).toList();
    return ResponseEntity.ok(responses);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  private RefreshTokenResponse toResponse(RefreshToken entity) {
    return RefreshTokenResponse.builder()
        .id(entity.getId())
        .userId(entity.getUserId())
        .token(entity.getToken())
        .expiredAt(entity.getExpiredAt())
        .revoked(entity.isRevoked())
        .createdAt(entity.getCreatedAt())
        .build();
  }
}
