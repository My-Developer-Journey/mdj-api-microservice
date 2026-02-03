package com.diemyolo.auth_service.service;

import com.diemyolo.auth_service.entity.LoginHistory;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoginHistoryService {
  Optional<LoginHistory> findById(UUID id);

  List<LoginHistory> findAll();

  LoginHistory save(LoginHistory history);

  void deleteById(UUID id);
}
