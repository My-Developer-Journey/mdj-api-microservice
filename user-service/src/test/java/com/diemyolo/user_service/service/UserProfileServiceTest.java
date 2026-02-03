package com.diemyolo.user_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.diemyolo.user_service.entity.UserProfile;
import com.diemyolo.user_service.repository.UserProfileRepository;
import com.diemyolo.user_service.service.implementation.UserProfileServiceImpl;
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
class UserProfileServiceTest {

  @Mock private UserProfileRepository repository;

  @InjectMocks private UserProfileServiceImpl service;

  private UserProfile testProfile;
  private UUID testUserId;

  @BeforeEach
  void setUp() {
    testUserId = UUID.randomUUID();
    testProfile =
        UserProfile.builder()
            .userId(testUserId)
            .fullName("John Doe")
            .avatarUrl("https://example.com/avatar.jpg")
            .bio("Test bio")
            .build();
  }

  @Test
  void testFindById() {
    when(repository.findById(testUserId)).thenReturn(Optional.of(testProfile));

    Optional<UserProfile> result = service.findById(testUserId);

    assertTrue(result.isPresent());
    assertEquals(testProfile.getUserId(), result.get().getUserId());
    assertEquals(testProfile.getFullName(), result.get().getFullName());
    verify(repository, times(1)).findById(testUserId);
  }

  @Test
  void testFindByUserId() {
    when(repository.findByUserId(testUserId)).thenReturn(Optional.of(testProfile));

    Optional<UserProfile> result = service.findByUserId(testUserId);

    assertTrue(result.isPresent());
    assertEquals(testProfile.getUserId(), result.get().getUserId());
    verify(repository, times(1)).findByUserId(testUserId);
  }

  @Test
  void testFindAll() {
    List<UserProfile> profiles = List.of(testProfile);
    when(repository.findAll()).thenReturn(profiles);

    List<UserProfile> result = service.findAll();

    assertEquals(1, result.size());
    assertEquals(testProfile.getUserId(), result.get(0).getUserId());
    verify(repository, times(1)).findAll();
  }

  @Test
  void testSave() {
    when(repository.save(testProfile)).thenReturn(testProfile);

    UserProfile result = service.save(testProfile);

    assertNotNull(result);
    assertEquals(testProfile.getUserId(), result.getUserId());
    verify(repository, times(1)).save(testProfile);
  }

  @Test
  void testDeleteById() {
    doNothing().when(repository).deleteById(testUserId);

    service.deleteById(testUserId);

    verify(repository, times(1)).deleteById(testUserId);
  }
}
