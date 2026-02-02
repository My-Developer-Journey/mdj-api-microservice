package com.diemyolo.auth_service.service.implementation;

import com.diemyolo.auth_service.entity.RefreshToken;
import com.diemyolo.auth_service.repository.RefreshTokenRepository;
import com.diemyolo.auth_service.service.RefreshTokenService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

  private final RefreshTokenRepository repository;

  @Override
  public Optional<RefreshToken> findById(UUID id) {
    return repository.findById(id);
  }

  @Override
  public Optional<RefreshToken> findByToken(String token) {
    return repository.findByToken(token);
  }

  @Override
  public List<RefreshToken> findAll() {
    return repository.findAll();
  }

  @Override
  public RefreshToken save(RefreshToken token) {
    return repository.save(token);
  }

  @Override
  public void deleteById(UUID id) {
    repository.deleteById(id);
  }
}
