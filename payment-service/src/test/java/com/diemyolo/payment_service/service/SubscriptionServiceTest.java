package com.diemyolo.payment_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.diemyolo.payment_service.entity.Subscription;
import com.diemyolo.payment_service.repository.SubscriptionRepository;
import com.diemyolo.payment_service.service.implementation.SubscriptionServiceImpl;
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
class SubscriptionServiceTest {

  @Mock private SubscriptionRepository repository;

  @InjectMocks private SubscriptionServiceImpl service;

  private Subscription testSubscription;
  private UUID testId;

  @BeforeEach
  void setUp() {
    testId = UUID.randomUUID();
    testSubscription =
        Subscription.builder()
            .id(testId)
            .userId(UUID.randomUUID())
            .plan("Premium")
            .status(Subscription.SubscriptionStatus.ACTIVE)
            .startedAt(LocalDateTime.now())
            .expiredAt(LocalDateTime.now().plusDays(30))
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
  }

  @Test
  void testFindById() {
    when(repository.findById(testId)).thenReturn(Optional.of(testSubscription));

    Optional<Subscription> result = service.findById(testId);

    assertTrue(result.isPresent());
    assertEquals(testSubscription.getId(), result.get().getId());
    assertEquals(testSubscription.getPlan(), result.get().getPlan());
    verify(repository, times(1)).findById(testId);
  }

  @Test
  void testFindAll() {
    List<Subscription> subscriptions = List.of(testSubscription);
    when(repository.findAll()).thenReturn(subscriptions);

    List<Subscription> result = service.findAll();

    assertEquals(1, result.size());
    verify(repository, times(1)).findAll();
  }

  @Test
  void testSave() {
    when(repository.save(testSubscription)).thenReturn(testSubscription);

    Subscription result = service.save(testSubscription);

    assertNotNull(result);
    assertEquals(testSubscription.getId(), result.getId());
    verify(repository, times(1)).save(testSubscription);
  }

  @Test
  void testDeleteById() {
    doNothing().when(repository).deleteById(testId);

    service.deleteById(testId);

    verify(repository, times(1)).deleteById(testId);
  }
}
