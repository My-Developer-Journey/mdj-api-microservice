package com.diemyolo.media_service.service;

import com.diemyolo.media_service.entity.MediaFile;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MediaFileService {
  Optional<MediaFile> findById(UUID id);

  List<MediaFile> findAll();

  MediaFile save(MediaFile file);

  void deleteById(UUID id);
}
