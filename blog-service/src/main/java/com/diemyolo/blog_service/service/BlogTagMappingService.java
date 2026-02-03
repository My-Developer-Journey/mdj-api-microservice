package com.diemyolo.blog_service.service;

import com.diemyolo.blog_service.entity.BlogTagMapping;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BlogTagMappingService {
  Optional<BlogTagMapping> findById(UUID id);

  List<BlogTagMapping> findAll();

  BlogTagMapping save(BlogTagMapping mapping);

  void deleteById(UUID id);
}
