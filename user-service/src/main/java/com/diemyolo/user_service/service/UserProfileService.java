package com.diemyolo.user_service.service;

import com.diemyolo.user_service.entity.UserProfile;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserProfileService {
  Optional<UserProfile> findById(UUID id);

  Optional<UserProfile> findByUserId(UUID userId);

  List<UserProfile> findAll();

  UserProfile save(UserProfile profile);

  void deleteById(UUID id);
}
