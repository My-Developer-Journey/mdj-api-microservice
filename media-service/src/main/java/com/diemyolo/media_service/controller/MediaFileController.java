package com.diemyolo.media_service.controller;

import com.diemyolo.media_service.entity.MediaFile;
import com.diemyolo.media_service.model.request.MediaFileRequest;
import com.diemyolo.media_service.model.response.MediaFileResponse;
import com.diemyolo.media_service.service.MediaFileService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/media-files")
@RequiredArgsConstructor
public class MediaFileController {

  private final MediaFileService service;

  @PostMapping
  public ResponseEntity<MediaFileResponse> create(@RequestBody MediaFileRequest request) {
    // Implementation will be handled later
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<MediaFileResponse> getById(@PathVariable UUID id) {
    return service
        .findById(id)
        .map(this::toResponse)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<MediaFileResponse>> getAll() {
    List<MediaFileResponse> responses = service.findAll().stream().map(this::toResponse).toList();
    return ResponseEntity.ok(responses);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  private MediaFileResponse toResponse(MediaFile entity) {
    return MediaFileResponse.builder()
        .id(entity.getId())
        .ownerId(entity.getOwnerId())
        .s3Key(entity.getS3Key())
        .url(entity.getUrl())
        .fileType(entity.getFileType())
        .fileSize(entity.getFileSize())
        .createdAt(entity.getCreatedAt())
        .build();
  }
}
