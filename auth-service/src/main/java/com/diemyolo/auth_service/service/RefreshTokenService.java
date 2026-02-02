package com.diemyolo.auth_service.service;

import com.diemyolo.auth_service.entity.RefreshToken;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenService {
  Optional<RefreshToken> findById(UUID id);

  Optional<RefreshToken> findByToken(String token);

  List<RefreshToken> findAll();

  RefreshToken save(RefreshToken token);

  void deleteById(UUID id);
}
