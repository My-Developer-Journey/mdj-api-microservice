package com.diemyolo.auth_service.service.implementation;

import com.diemyolo.auth_service.entity.LoginHistory;
import com.diemyolo.auth_service.repository.LoginHistoryRepository;
import com.diemyolo.auth_service.service.LoginHistoryService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginHistoryServiceImpl implements LoginHistoryService {

  private final LoginHistoryRepository repository;

  @Override
  public Optional<LoginHistory> findById(UUID id) {
    return repository.findById(id);
  }

  @Override
  public List<LoginHistory> findAll() {
    return repository.findAll();
  }

  @Override
  public LoginHistory save(LoginHistory history) {
    return repository.save(history);
  }

  @Override
  public void deleteById(UUID id) {
    repository.deleteById(id);
  }
}
