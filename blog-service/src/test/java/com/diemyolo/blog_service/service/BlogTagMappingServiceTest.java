package com.diemyolo.blog_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.diemyolo.blog_service.entity.BlogTagMapping;
import com.diemyolo.blog_service.repository.BlogTagMappingRepository;
import com.diemyolo.blog_service.service.implementation.BlogTagMappingServiceImpl;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BlogTagMappingServiceTest {

  @Mock private BlogTagMappingRepository repository;

  @InjectMocks private BlogTagMappingServiceImpl service;

  private BlogTagMapping testMapping;
  private UUID testId;

  @BeforeEach
  void setUp() {
    testId = UUID.randomUUID();
    testMapping =
        BlogTagMapping.builder()
            .id(testId)
            .blogId(UUID.randomUUID())
            .tagId(UUID.randomUUID())
            .build();
  }

  @Test
  void testFindById() {
    when(repository.findById(testId)).thenReturn(Optional.of(testMapping));

    Optional<BlogTagMapping> result = service.findById(testId);

    assertTrue(result.isPresent());
    assertEquals(testMapping.getId(), result.get().getId());
    assertEquals(testMapping.getBlogId(), result.get().getBlogId());
    verify(repository, times(1)).findById(testId);
  }

  @Test
  void testFindAll() {
    List<BlogTagMapping> mappings = List.of(testMapping);
    when(repository.findAll()).thenReturn(mappings);

    List<BlogTagMapping> result = service.findAll();

    assertEquals(1, result.size());
    verify(repository, times(1)).findAll();
  }

  @Test
  void testSave() {
    when(repository.save(testMapping)).thenReturn(testMapping);

    BlogTagMapping result = service.save(testMapping);

    assertNotNull(result);
    assertEquals(testMapping.getId(), result.getId());
    verify(repository, times(1)).save(testMapping);
  }

  @Test
  void testDeleteById() {
    doNothing().when(repository).deleteById(testId);

    service.deleteById(testId);

    verify(repository, times(1)).deleteById(testId);
  }
}
