package com.diemyolo.blog_service.service;

import com.diemyolo.blog_service.entity.Blog;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BlogService {
  Optional<Blog> findById(UUID id);

  List<Blog> findAll();

  Blog save(Blog blog);

  void deleteById(UUID id);
}
