package com.diemyolo.payment_service.service;

import com.diemyolo.payment_service.entity.Subscription;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubscriptionService {
  Optional<Subscription> findById(UUID id);

  List<Subscription> findAll();

  Subscription save(Subscription subscription);

  void deleteById(UUID id);
}
