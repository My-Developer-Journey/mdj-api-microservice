package com.diemyolo.payment_service.controller;

import com.diemyolo.payment_service.entity.Subscription;
import com.diemyolo.payment_service.model.request.SubscriptionRequest;
import com.diemyolo.payment_service.model.response.SubscriptionResponse;
import com.diemyolo.payment_service.service.SubscriptionService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

  private final SubscriptionService service;

  @PostMapping
  public ResponseEntity<SubscriptionResponse> create(@RequestBody SubscriptionRequest request) {
    // Implementation will be handled later
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<SubscriptionResponse> getById(@PathVariable UUID id) {
    return service
        .findById(id)
        .map(this::toResponse)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<SubscriptionResponse>> getAll() {
    List<SubscriptionResponse> responses =
        service.findAll().stream().map(this::toResponse).toList();
    return ResponseEntity.ok(responses);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  private SubscriptionResponse toResponse(Subscription entity) {
    return SubscriptionResponse.builder()
        .id(entity.getId())
        .userId(entity.getUserId())
        .plan(entity.getPlan())
        .status(entity.getStatus().name())
        .startedAt(entity.getStartedAt())
        .expiredAt(entity.getExpiredAt())
        .createdAt(entity.getCreatedAt())
        .updatedAt(entity.getUpdatedAt())
        .build();
  }
}
