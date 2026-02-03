package com.diemyolo.auth_service.controller;

import com.diemyolo.auth_service.entity.LoginHistory;
import com.diemyolo.auth_service.model.request.LoginHistoryRequest;
import com.diemyolo.auth_service.model.response.LoginHistoryResponse;
import com.diemyolo.auth_service.service.LoginHistoryService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/login-history")
@RequiredArgsConstructor
public class LoginHistoryController {

  private final LoginHistoryService service;

  @PostMapping
  public ResponseEntity<LoginHistoryResponse> create(@RequestBody LoginHistoryRequest request) {
    // Implementation will be handled later
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<LoginHistoryResponse> getById(@PathVariable UUID id) {
    return service
        .findById(id)
        .map(this::toResponse)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<LoginHistoryResponse>> getAll() {
    List<LoginHistoryResponse> responses =
        service.findAll().stream().map(this::toResponse).toList();
    return ResponseEntity.ok(responses);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  private LoginHistoryResponse toResponse(LoginHistory entity) {
    return LoginHistoryResponse.builder()
        .id(entity.getId())
        .userId(entity.getUserId())
        .ipAddress(entity.getIpAddress())
        .userAgent(entity.getUserAgent())
        .loginAt(entity.getLoginAt())
        .createdAt(entity.getCreatedAt())
        .build();
  }
}
