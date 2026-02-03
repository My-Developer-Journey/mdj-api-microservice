package com.diemyolo.auth_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.diemyolo.auth_service.entity.RefreshToken;
import com.diemyolo.auth_service.repository.RefreshTokenRepository;
import com.diemyolo.auth_service.service.implementation.RefreshTokenServiceImpl;
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
class RefreshTokenServiceTest {

  @Mock private RefreshTokenRepository repository;

  @InjectMocks private RefreshTokenServiceImpl service;

  private RefreshToken testToken;
  private UUID testId;

  @BeforeEach
  void setUp() {
    testId = UUID.randomUUID();
    testToken =
        RefreshToken.builder()
            .id(testId)
            .userId(UUID.randomUUID())
            .token("test-token")
            .expiredAt(LocalDateTime.now().plusDays(7))
            .revoked(false)
            .createdAt(LocalDateTime.now())
            .build();
  }

  @Test
  void testFindById() {
    when(repository.findById(testId)).thenReturn(Optional.of(testToken));

    Optional<RefreshToken> result = service.findById(testId);

    assertTrue(result.isPresent());
    assertEquals(testToken.getId(), result.get().getId());
    assertEquals(testToken.getToken(), result.get().getToken());
    verify(repository, times(1)).findById(testId);
  }

  @Test
  void testFindByToken() {
    when(repository.findByToken("test-token")).thenReturn(Optional.of(testToken));

    Optional<RefreshToken> result = service.findByToken("test-token");

    assertTrue(result.isPresent());
    assertEquals(testToken.getToken(), result.get().getToken());
    verify(repository, times(1)).findByToken("test-token");
  }

  @Test
  void testFindAll() {
    List<RefreshToken> tokens = List.of(testToken);
    when(repository.findAll()).thenReturn(tokens);

    List<RefreshToken> result = service.findAll();

    assertEquals(1, result.size());
    verify(repository, times(1)).findAll();
  }

  @Test
  void testSave() {
    when(repository.save(testToken)).thenReturn(testToken);

    RefreshToken result = service.save(testToken);

    assertNotNull(result);
    assertEquals(testToken.getId(), result.getId());
    verify(repository, times(1)).save(testToken);
  }

  @Test
  void testDeleteById() {
    doNothing().when(repository).deleteById(testId);

    service.deleteById(testId);

    verify(repository, times(1)).deleteById(testId);
  }
}
