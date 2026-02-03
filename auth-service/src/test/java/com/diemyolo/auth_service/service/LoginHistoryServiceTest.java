package com.diemyolo.auth_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.diemyolo.auth_service.entity.LoginHistory;
import com.diemyolo.auth_service.repository.LoginHistoryRepository;
import com.diemyolo.auth_service.service.implementation.LoginHistoryServiceImpl;
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
class LoginHistoryServiceTest {

  @Mock private LoginHistoryRepository repository;

  @InjectMocks private LoginHistoryServiceImpl service;

  private LoginHistory testHistory;
  private UUID testId;

  @BeforeEach
  void setUp() {
    testId = UUID.randomUUID();
    testHistory =
        LoginHistory.builder()
            .id(testId)
            .userId(UUID.randomUUID())
            .ipAddress("192.168.1.1")
            .userAgent("Mozilla/5.0")
            .loginAt(LocalDateTime.now())
            .createdAt(LocalDateTime.now())
            .build();
  }

  @Test
  void testFindById() {
    when(repository.findById(testId)).thenReturn(Optional.of(testHistory));

    Optional<LoginHistory> result = service.findById(testId);

    assertTrue(result.isPresent());
    assertEquals(testHistory.getId(), result.get().getId());
    assertEquals(testHistory.getIpAddress(), result.get().getIpAddress());
    verify(repository, times(1)).findById(testId);
  }

  @Test
  void testFindAll() {
    List<LoginHistory> histories = List.of(testHistory);
    when(repository.findAll()).thenReturn(histories);

    List<LoginHistory> result = service.findAll();

    assertEquals(1, result.size());
    verify(repository, times(1)).findAll();
  }

  @Test
  void testSave() {
    when(repository.save(testHistory)).thenReturn(testHistory);

    LoginHistory result = service.save(testHistory);

    assertNotNull(result);
    assertEquals(testHistory.getId(), result.getId());
    verify(repository, times(1)).save(testHistory);
  }

  @Test
  void testDeleteById() {
    doNothing().when(repository).deleteById(testId);

    service.deleteById(testId);

    verify(repository, times(1)).deleteById(testId);
  }
}
