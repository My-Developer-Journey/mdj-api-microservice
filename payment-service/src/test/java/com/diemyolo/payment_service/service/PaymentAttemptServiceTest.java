package com.diemyolo.payment_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.diemyolo.payment_service.entity.PaymentAttempt;
import com.diemyolo.payment_service.repository.PaymentAttemptRepository;
import com.diemyolo.payment_service.service.implementation.PaymentAttemptServiceImpl;
import java.math.BigDecimal;
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
class PaymentAttemptServiceTest {

  @Mock private PaymentAttemptRepository repository;

  @InjectMocks private PaymentAttemptServiceImpl service;

  private PaymentAttempt testAttempt;
  private UUID testId;

  @BeforeEach
  void setUp() {
    testId = UUID.randomUUID();
    testAttempt =
        PaymentAttempt.builder()
            .id(testId)
            .userId(UUID.randomUUID())
            .provider(PaymentAttempt.PaymentProvider.STRIPE)
            .amount(BigDecimal.valueOf(99.99))
            .currency("USD")
            .status(PaymentAttempt.PaymentStatus.SUCCESS)
            .providerTxnId("txn_123")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
  }

  @Test
  void testFindById() {
    when(repository.findById(testId)).thenReturn(Optional.of(testAttempt));

    Optional<PaymentAttempt> result = service.findById(testId);

    assertTrue(result.isPresent());
    assertEquals(testAttempt.getId(), result.get().getId());
    assertEquals(testAttempt.getAmount(), result.get().getAmount());
    verify(repository, times(1)).findById(testId);
  }

  @Test
  void testFindAll() {
    List<PaymentAttempt> attempts = List.of(testAttempt);
    when(repository.findAll()).thenReturn(attempts);

    List<PaymentAttempt> result = service.findAll();

    assertEquals(1, result.size());
    verify(repository, times(1)).findAll();
  }

  @Test
  void testSave() {
    when(repository.save(testAttempt)).thenReturn(testAttempt);

    PaymentAttempt result = service.save(testAttempt);

    assertNotNull(result);
    assertEquals(testAttempt.getId(), result.getId());
    verify(repository, times(1)).save(testAttempt);
  }

  @Test
  void testDeleteById() {
    doNothing().when(repository).deleteById(testId);

    service.deleteById(testId);

    verify(repository, times(1)).deleteById(testId);
  }
}
