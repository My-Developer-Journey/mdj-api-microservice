package com.diemyolo.blog_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.diemyolo.blog_service.entity.Blog;
import com.diemyolo.blog_service.repository.BlogRepository;
import com.diemyolo.blog_service.service.implementation.BlogServiceImpl;
import java.time.LocalDateTime;
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
class BlogServiceTest {

  @Mock private BlogRepository repository;

  @InjectMocks private BlogServiceImpl service;

  private Blog testBlog;
  private UUID testId;

  @BeforeEach
  void setUp() {
    testId = UUID.randomUUID();
    testBlog =
        Blog.builder()
            .id(testId)
            .authorId(UUID.randomUUID())
            .title("Test Blog")
            .slug("test-blog")
            .content("Test content")
            .status(Blog.BlogStatus.DRAFT)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
  }

  @Test
  void testFindById() {
    when(repository.findById(testId)).thenReturn(Optional.of(testBlog));

    Optional<Blog> result = service.findById(testId);

    assertTrue(result.isPresent());
    assertEquals(testBlog.getId(), result.get().getId());
    assertEquals(testBlog.getTitle(), result.get().getTitle());
    verify(repository, times(1)).findById(testId);
  }

  @Test
  void testFindAll() {
    List<Blog> blogs = List.of(testBlog);
    when(repository.findAll()).thenReturn(blogs);

    List<Blog> result = service.findAll();

    assertEquals(1, result.size());
    assertEquals(testBlog.getId(), result.get(0).getId());
    verify(repository, times(1)).findAll();
  }

  @Test
  void testSave() {
    when(repository.save(testBlog)).thenReturn(testBlog);

    Blog result = service.save(testBlog);

    assertNotNull(result);
    assertEquals(testBlog.getId(), result.getId());
    verify(repository, times(1)).save(testBlog);
  }

  @Test
  void testDeleteById() {
    doNothing().when(repository).deleteById(testId);

    service.deleteById(testId);

    verify(repository, times(1)).deleteById(testId);
  }
}
