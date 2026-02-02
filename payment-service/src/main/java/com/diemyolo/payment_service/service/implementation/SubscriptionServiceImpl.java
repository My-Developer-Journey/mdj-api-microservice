package com.diemyolo.payment_service.service.implementation;

import com.diemyolo.payment_service.entity.Subscription;
import com.diemyolo.payment_service.repository.SubscriptionRepository;
import com.diemyolo.payment_service.service.SubscriptionService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

  private final SubscriptionRepository repository;

  @Override
  public Optional<Subscription> findById(UUID id) {
    return repository.findById(id);
  }

  @Override
  public List<Subscription> findAll() {
    return repository.findAll();
  }

  @Override
  public Subscription save(Subscription subscription) {
    return repository.save(subscription);
  }

  @Override
  public void deleteById(UUID id) {
    repository.deleteById(id);
  }
}
