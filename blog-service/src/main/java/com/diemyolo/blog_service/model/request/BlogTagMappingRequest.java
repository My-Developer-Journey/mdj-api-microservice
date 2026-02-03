package com.diemyolo.blog_service.model.request;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogTagMappingRequest {
  private UUID blogId;
  private UUID tagId;
}
