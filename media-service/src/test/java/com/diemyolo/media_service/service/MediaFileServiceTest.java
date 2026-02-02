package com.diemyolo.media_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.diemyolo.media_service.entity.MediaFile;
import com.diemyolo.media_service.repository.MediaFileRepository;
import com.diemyolo.media_service.service.implementation.MediaFileServiceImpl;
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
class MediaFileServiceTest {

  @Mock private MediaFileRepository repository;

  @InjectMocks private MediaFileServiceImpl service;

  private MediaFile testFile;
  private UUID testId;

  @BeforeEach
  void setUp() {
    testId = UUID.randomUUID();
    testFile =
        MediaFile.builder()
            .id(testId)
            .ownerId(UUID.randomUUID())
            .s3Key("test-key")
            .url("https://s3.example.com/test")
            .fileType("image/png")
            .fileSize(1024L)
            .createdAt(LocalDateTime.now())
            .build();
  }

  @Test
  void testFindById() {
    when(repository.findById(testId)).thenReturn(Optional.of(testFile));

    Optional<MediaFile> result = service.findById(testId);

    assertTrue(result.isPresent());
    assertEquals(testFile.getId(), result.get().getId());
    assertEquals(testFile.getS3Key(), result.get().getS3Key());
    verify(repository, times(1)).findById(testId);
  }

  @Test
  void testFindAll() {
    List<MediaFile> files = List.of(testFile);
    when(repository.findAll()).thenReturn(files);

    List<MediaFile> result = service.findAll();

    assertEquals(1, result.size());
    verify(repository, times(1)).findAll();
  }

  @Test
  void testSave() {
    when(repository.save(testFile)).thenReturn(testFile);

    MediaFile result = service.save(testFile);

    assertNotNull(result);
    assertEquals(testFile.getId(), result.getId());
    verify(repository, times(1)).save(testFile);
  }

  @Test
  void testDeleteById() {
    doNothing().when(repository).deleteById(testId);

    service.deleteById(testId);

    verify(repository, times(1)).deleteById(testId);
  }
}
