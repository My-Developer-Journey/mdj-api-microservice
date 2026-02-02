package com.diemyolo.payment_service.repository;

import com.diemyolo.payment_service.entity.Subscription;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {}
