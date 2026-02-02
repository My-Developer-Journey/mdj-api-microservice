package com.diemyolo.media_service.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaFileRequest {
  private String s3Key;
  private String url;
  private String fileType;
  private Long fileSize;
}
