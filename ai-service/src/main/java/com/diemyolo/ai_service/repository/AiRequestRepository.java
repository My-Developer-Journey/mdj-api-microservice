package com.diemyolo.ai_service.repository;

import com.diemyolo.ai_service.entity.AiRequest;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiRequestRepository extends JpaRepository<AiRequest, UUID> {}
