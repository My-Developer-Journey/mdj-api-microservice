package com.diemyolo.blog_service.controller;

import com.diemyolo.blog_service.entity.Blog;
import com.diemyolo.blog_service.model.request.BlogRequest;
import com.diemyolo.blog_service.model.response.BlogResponse;
import com.diemyolo.blog_service.service.BlogService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/blogs")
@RequiredArgsConstructor
public class BlogController {

  private final BlogService service;

  @PostMapping
  public ResponseEntity<BlogResponse> create(@RequestBody BlogRequest request) {
    // Implementation will be handled later
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<BlogResponse> getById(@PathVariable UUID id) {
    return service
        .findById(id)
        .map(this::toResponse)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<BlogResponse>> getAll() {
    List<BlogResponse> responses = service.findAll().stream().map(this::toResponse).toList();
    return ResponseEntity.ok(responses);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  private BlogResponse toResponse(Blog entity) {
    return BlogResponse.builder()
        .id(entity.getId())
        .authorId(entity.getAuthorId())
        .title(entity.getTitle())
        .slug(entity.getSlug())
        .content(entity.getContent())
        .status(entity.getStatus().name())
        .publishedAt(entity.getPublishedAt())
        .createdAt(entity.getCreatedAt())
        .updatedAt(entity.getUpdatedAt())
        .build();
  }
}
