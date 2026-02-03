package com.diemyolo.user_service.service.implementation;

import com.diemyolo.user_service.entity.UserProfile;
import com.diemyolo.user_service.repository.UserProfileRepository;
import com.diemyolo.user_service.service.UserProfileService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

  private final UserProfileRepository repository;

  @Override
  public Optional<UserProfile> findById(UUID id) {
    return repository.findById(id);
  }

  @Override
  public Optional<UserProfile> findByUserId(UUID userId) {
    return repository.findByUserId(userId);
  }

  @Override
  public List<UserProfile> findAll() {
    return repository.findAll();
  }

  @Override
  public UserProfile save(UserProfile profile) {
    return repository.save(profile);
  }

  @Override
  public void deleteById(UUID id) {
    repository.deleteById(id);
  }
}
