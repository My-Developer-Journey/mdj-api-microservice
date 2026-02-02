package com.diemyolo.blog_service.service.implementation;

import com.diemyolo.blog_service.entity.Blog;
import com.diemyolo.blog_service.repository.BlogRepository;
import com.diemyolo.blog_service.service.BlogService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

  private final BlogRepository repository;

  @Override
  public Optional<Blog> findById(UUID id) {
    return repository.findById(id);
  }

  @Override
  public List<Blog> findAll() {
    return repository.findAll();
  }

  @Override
  public Blog save(Blog blog) {
    return repository.save(blog);
  }

  @Override
  public void deleteById(UUID id) {
    repository.deleteById(id);
  }
}
