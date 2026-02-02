package com.diemyolo.blog_service.model.response;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogTagMappingResponse {
  private UUID id;
  private UUID blogId;
  private UUID tagId;
}
