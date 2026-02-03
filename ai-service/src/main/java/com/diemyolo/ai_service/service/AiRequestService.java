package com.diemyolo.ai_service.service;

import com.diemyolo.ai_service.entity.AiRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AiRequestService {
  Optional<AiRequest> findById(UUID id);

  List<AiRequest> findAll();

  AiRequest save(AiRequest request);

  void deleteById(UUID id);
}
