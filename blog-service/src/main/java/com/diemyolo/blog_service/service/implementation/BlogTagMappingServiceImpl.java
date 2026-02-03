package com.diemyolo.blog_service.service.implementation;

import com.diemyolo.blog_service.entity.BlogTagMapping;
import com.diemyolo.blog_service.repository.BlogTagMappingRepository;
import com.diemyolo.blog_service.service.BlogTagMappingService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogTagMappingServiceImpl implements BlogTagMappingService {

  private final BlogTagMappingRepository repository;

  @Override
  public Optional<BlogTagMapping> findById(UUID id) {
    return repository.findById(id);
  }

  @Override
  public List<BlogTagMapping> findAll() {
    return repository.findAll();
  }

  @Override
  public BlogTagMapping save(BlogTagMapping mapping) {
    return repository.save(mapping);
  }

  @Override
  public void deleteById(UUID id) {
    repository.deleteById(id);
  }
}
