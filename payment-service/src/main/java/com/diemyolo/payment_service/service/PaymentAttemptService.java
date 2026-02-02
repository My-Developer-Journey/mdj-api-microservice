package com.diemyolo.payment_service.service;

import com.diemyolo.payment_service.entity.PaymentAttempt;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentAttemptService {
  Optional<PaymentAttempt> findById(UUID id);

  List<PaymentAttempt> findAll();

  PaymentAttempt save(PaymentAttempt attempt);

  void deleteById(UUID id);
}
