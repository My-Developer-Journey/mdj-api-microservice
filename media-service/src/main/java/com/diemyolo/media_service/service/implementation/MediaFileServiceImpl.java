package com.diemyolo.media_service.service.implementation;

import com.diemyolo.media_service.entity.MediaFile;
import com.diemyolo.media_service.repository.MediaFileRepository;
import com.diemyolo.media_service.service.MediaFileService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MediaFileServiceImpl implements MediaFileService {

  private final MediaFileRepository repository;

  @Override
  public Optional<MediaFile> findById(UUID id) {
    return repository.findById(id);
  }

  @Override
  public List<MediaFile> findAll() {
    return repository.findAll();
  }

  @Override
  public MediaFile save(MediaFile file) {
    return repository.save(file);
  }

  @Override
  public void deleteById(UUID id) {
    repository.deleteById(id);
  }
}
