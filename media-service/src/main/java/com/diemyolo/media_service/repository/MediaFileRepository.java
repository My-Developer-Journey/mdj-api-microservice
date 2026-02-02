package com.diemyolo.media_service.repository;

import com.diemyolo.media_service.entity.MediaFile;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaFileRepository extends JpaRepository<MediaFile, UUID> {}
