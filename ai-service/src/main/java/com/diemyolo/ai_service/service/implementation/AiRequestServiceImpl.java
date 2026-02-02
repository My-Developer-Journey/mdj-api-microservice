package com.diemyolo.ai_service.service.implementation;

import com.diemyolo.ai_service.entity.AiRequest;
import com.diemyolo.ai_service.repository.AiRequestRepository;
import com.diemyolo.ai_service.service.AiRequestService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiRequestServiceImpl implements AiRequestService {

  private final AiRequestRepository repository;

  @Override
  public Optional<AiRequest> findById(UUID id) {
    return repository.findById(id);
  }

  @Override
  public List<AiRequest> findAll() {
    return repository.findAll();
  }

  @Override
  public AiRequest save(AiRequest request) {
    return repository.save(request);
  }

  @Override
  public void deleteById(UUID id) {
    repository.deleteById(id);
  }
}
