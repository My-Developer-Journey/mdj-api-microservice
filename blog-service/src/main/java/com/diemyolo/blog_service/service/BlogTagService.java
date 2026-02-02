package com.diemyolo.blog_service.service;

import com.diemyolo.blog_service.entity.BlogTag;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BlogTagService {
  Optional<BlogTag> findById(UUID id);

  Optional<BlogTag> findByName(String name);

  List<BlogTag> findAll();

  BlogTag save(BlogTag tag);

  void deleteById(UUID id);
}
