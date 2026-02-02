package com.diemyolo.payment_service.controller;

import com.diemyolo.payment_service.entity.PaymentAttempt;
import com.diemyolo.payment_service.model.request.PaymentAttemptRequest;
import com.diemyolo.payment_service.model.response.PaymentAttemptResponse;
import com.diemyolo.payment_service.service.PaymentAttemptService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment-attempts")
@RequiredArgsConstructor
public class PaymentAttemptController {

  private final PaymentAttemptService service;

  @PostMapping
  public ResponseEntity<PaymentAttemptResponse> create(@RequestBody PaymentAttemptRequest request) {
    // Implementation will be handled later
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<PaymentAttemptResponse> getById(@PathVariable UUID id) {
    return service
        .findById(id)
        .map(this::toResponse)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<PaymentAttemptResponse>> getAll() {
    List<PaymentAttemptResponse> responses =
        service.findAll().stream().map(this::toResponse).toList();
    return ResponseEntity.ok(responses);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  private PaymentAttemptResponse toResponse(PaymentAttempt entity) {
    return PaymentAttemptResponse.builder()
        .id(entity.getId())
        .userId(entity.getUserId())
        .provider(entity.getProvider().name())
        .amount(entity.getAmount())
        .currency(entity.getCurrency())
        .status(entity.getStatus().name())
        .providerTxnId(entity.getProviderTxnId())
        .createdAt(entity.getCreatedAt())
        .updatedAt(entity.getUpdatedAt())
        .build();
  }
}
