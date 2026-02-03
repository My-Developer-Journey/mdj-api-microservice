package com.diemyolo.payment_service.repository;

import com.diemyolo.payment_service.entity.PaymentAttempt;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentAttemptRepository extends JpaRepository<PaymentAttempt, UUID> {}
