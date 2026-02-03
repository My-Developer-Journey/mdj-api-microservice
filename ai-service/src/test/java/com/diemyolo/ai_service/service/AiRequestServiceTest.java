package com.diemyolo.ai_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.diemyolo.ai_service.entity.AiRequest;
import com.diemyolo.ai_service.repository.AiRequestRepository;
import com.diemyolo.ai_service.service.implementation.AiRequestServiceImpl;
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
class AiRequestServiceTest {

  @Mock private AiRequestRepository repository;

  @InjectMocks private AiRequestServiceImpl service;

  private AiRequest testRequest;
  private UUID testId;

  @BeforeEach
  void setUp() {
    testId = UUID.randomUUID();
    testRequest =
        AiRequest.builder()
            .id(testId)
            .userId(UUID.randomUUID())
            .prompt("Test prompt")
            .response("Test response")
            .model("gpt-3.5")
            .tokensUsed(100)
            .createdAt(LocalDateTime.now())
            .build();
  }

  @Test
  void testFindById() {
    when(repository.findById(testId)).thenReturn(Optional.of(testRequest));

    Optional<AiRequest> result = service.findById(testId);

    assertTrue(result.isPresent());
    assertEquals(testRequest.getId(), result.get().getId());
    assertEquals(testRequest.getPrompt(), result.get().getPrompt());
    verify(repository, times(1)).findById(testId);
  }

  @Test
  void testFindByIdNotFound() {
    when(repository.findById(testId)).thenReturn(Optional.empty());

    Optional<AiRequest> result = service.findById(testId);

    assertFalse(result.isPresent());
    verify(repository, times(1)).findById(testId);
  }

  @Test
  void testFindAll() {
    List<AiRequest> requests = List.of(testRequest);
    when(repository.findAll()).thenReturn(requests);

    List<AiRequest> result = service.findAll();

    assertEquals(1, result.size());
    assertEquals(testRequest.getId(), result.get(0).getId());
    verify(repository, times(1)).findAll();
  }

  @Test
  void testSave() {
    when(repository.save(testRequest)).thenReturn(testRequest);

    AiRequest result = service.save(testRequest);

    assertNotNull(result);
    assertEquals(testRequest.getId(), result.getId());
    verify(repository, times(1)).save(testRequest);
  }

  @Test
  void testDeleteById() {
    doNothing().when(repository).deleteById(testId);

    service.deleteById(testId);

    verify(repository, times(1)).deleteById(testId);
  }
}
