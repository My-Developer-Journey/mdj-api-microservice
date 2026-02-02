package com.diemyolo.payment_service.service.implementation;

import com.diemyolo.payment_service.entity.PaymentAttempt;
import com.diemyolo.payment_service.repository.PaymentAttemptRepository;
import com.diemyolo.payment_service.service.PaymentAttemptService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentAttemptServiceImpl implements PaymentAttemptService {

  private final PaymentAttemptRepository repository;

  @Override
  public Optional<PaymentAttempt> findById(UUID id) {
    return repository.findById(id);
  }

  @Override
  public List<PaymentAttempt> findAll() {
    return repository.findAll();
  }

  @Override
  public PaymentAttempt save(PaymentAttempt attempt) {
    return repository.save(attempt);
  }

  @Override
  public void deleteById(UUID id) {
    repository.deleteById(id);
  }
}
