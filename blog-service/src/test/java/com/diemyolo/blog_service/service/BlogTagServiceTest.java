package com.diemyolo.blog_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.diemyolo.blog_service.entity.BlogTag;
import com.diemyolo.blog_service.repository.BlogTagRepository;
import com.diemyolo.blog_service.service.implementation.BlogTagServiceImpl;
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
class BlogTagServiceTest {

  @Mock private BlogTagRepository repository;

  @InjectMocks private BlogTagServiceImpl service;

  private BlogTag testTag;
  private UUID testId;

  @BeforeEach
  void setUp() {
    testId = UUID.randomUUID();
    testTag = BlogTag.builder().id(testId).name("Java").build();
  }

  @Test
  void testFindById() {
    when(repository.findById(testId)).thenReturn(Optional.of(testTag));

    Optional<BlogTag> result = service.findById(testId);

    assertTrue(result.isPresent());
    assertEquals(testTag.getId(), result.get().getId());
    assertEquals(testTag.getName(), result.get().getName());
    verify(repository, times(1)).findById(testId);
  }

  @Test
  void testFindByName() {
    when(repository.findByName("Java")).thenReturn(Optional.of(testTag));

    Optional<BlogTag> result = service.findByName("Java");

    assertTrue(result.isPresent());
    assertEquals(testTag.getName(), result.get().getName());
    verify(repository, times(1)).findByName("Java");
  }

  @Test
  void testFindAll() {
    List<BlogTag> tags = List.of(testTag);
    when(repository.findAll()).thenReturn(tags);

    List<BlogTag> result = service.findAll();

    assertEquals(1, result.size());
    verify(repository, times(1)).findAll();
  }

  @Test
  void testSave() {
    when(repository.save(testTag)).thenReturn(testTag);

    BlogTag result = service.save(testTag);

    assertNotNull(result);
    assertEquals(testTag.getId(), result.getId());
    verify(repository, times(1)).save(testTag);
  }

  @Test
  void testDeleteById() {
    doNothing().when(repository).deleteById(testId);

    service.deleteById(testId);

    verify(repository, times(1)).deleteById(testId);
  }
}
