package com.diemyolo.blog_service.controller;

import com.diemyolo.blog_service.entity.BlogTag;
import com.diemyolo.blog_service.model.request.BlogTagRequest;
import com.diemyolo.blog_service.model.response.BlogTagResponse;
import com.diemyolo.blog_service.service.BlogTagService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/blog-tags")
@RequiredArgsConstructor
public class BlogTagController {

  private final BlogTagService service;

  @PostMapping
  public ResponseEntity<BlogTagResponse> create(@RequestBody BlogTagRequest request) {
    // Implementation will be handled later
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<BlogTagResponse> getById(@PathVariable UUID id) {
    return service
        .findById(id)
        .map(this::toResponse)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<BlogTagResponse>> getAll() {
    List<BlogTagResponse> responses = service.findAll().stream().map(this::toResponse).toList();
    return ResponseEntity.ok(responses);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  private BlogTagResponse toResponse(BlogTag entity) {
    return BlogTagResponse.builder().id(entity.getId()).name(entity.getName()).build();
  }
}
