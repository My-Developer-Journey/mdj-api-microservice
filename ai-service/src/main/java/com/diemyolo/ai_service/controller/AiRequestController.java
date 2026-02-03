package com.diemyolo.ai_service.controller;

import com.diemyolo.ai_service.entity.AiRequest;
import com.diemyolo.ai_service.model.request.AiRequestRequest;
import com.diemyolo.ai_service.model.response.AiRequestResponse;
import com.diemyolo.ai_service.service.AiRequestService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai-requests")
@RequiredArgsConstructor
public class AiRequestController {

  private final AiRequestService service;

  @PostMapping
  public ResponseEntity<AiRequestResponse> create(@RequestBody AiRequestRequest request) {
    // Implement logic to create AI request
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<AiRequestResponse> getById(@PathVariable UUID id) {
    return service
        .findById(id)
        .map(this::toResponse)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<AiRequestResponse>> getAll() {
    List<AiRequestResponse> responses = service.findAll().stream().map(this::toResponse).toList();
    return ResponseEntity.ok(responses);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  private AiRequestResponse toResponse(AiRequest entity) {
    return AiRequestResponse.builder()
        .id(entity.getId())
        .userId(entity.getUserId())
        .prompt(entity.getPrompt())
        .response(entity.getResponse())
        .model(entity.getModel())
        .tokensUsed(entity.getTokensUsed())
        .createdAt(entity.getCreatedAt())
        .build();
  }
}
