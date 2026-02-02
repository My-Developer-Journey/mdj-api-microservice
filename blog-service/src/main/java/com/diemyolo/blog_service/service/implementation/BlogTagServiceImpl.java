package com.diemyolo.blog_service.service.implementation;

import com.diemyolo.blog_service.entity.BlogTag;
import com.diemyolo.blog_service.repository.BlogTagRepository;
import com.diemyolo.blog_service.service.BlogTagService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogTagServiceImpl implements BlogTagService {

  private final BlogTagRepository repository;

  @Override
  public Optional<BlogTag> findById(UUID id) {
    return repository.findById(id);
  }

  @Override
  public Optional<BlogTag> findByName(String name) {
    return repository.findByName(name);
  }

  @Override
  public List<BlogTag> findAll() {
    return repository.findAll();
  }

  @Override
  public BlogTag save(BlogTag tag) {
    return repository.save(tag);
  }

  @Override
  public void deleteById(UUID id) {
    repository.deleteById(id);
  }
}
