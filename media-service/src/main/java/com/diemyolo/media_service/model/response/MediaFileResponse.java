package com.diemyolo.media_service.model.response;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaFileResponse {
  private UUID id;
  private UUID ownerId;
  private String s3Key;
  private String url;
  private String fileType;
  private Long fileSize;
  private LocalDateTime createdAt;
}
