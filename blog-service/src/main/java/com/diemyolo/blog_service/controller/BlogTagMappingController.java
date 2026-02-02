package com.diemyolo.blog_service.controller;

import com.diemyolo.blog_service.entity.BlogTagMapping;
import com.diemyolo.blog_service.model.request.BlogTagMappingRequest;
import com.diemyolo.blog_service.model.response.BlogTagMappingResponse;
import com.diemyolo.blog_service.service.BlogTagMappingService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/blog-tag-mappings")
@RequiredArgsConstructor
public class BlogTagMappingController {

  private final BlogTagMappingService service;

  @PostMapping
  public ResponseEntity<BlogTagMappingResponse> create(@RequestBody BlogTagMappingRequest request) {
    // Implementation will be handled later
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<BlogTagMappingResponse> getById(@PathVariable UUID id) {
    return service
        .findById(id)
        .map(this::toResponse)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<BlogTagMappingResponse>> getAll() {
    List<BlogTagMappingResponse> responses =
        service.findAll().stream().map(this::toResponse).toList();
    return ResponseEntity.ok(responses);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  private BlogTagMappingResponse toResponse(BlogTagMapping entity) {
    return BlogTagMappingResponse.builder()
        .id(entity.getId())
        .blogId(entity.getBlogId())
        .tagId(entity.getTagId())
        .build();
  }
}
