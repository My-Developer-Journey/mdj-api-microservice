package com.diemyolo.media_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "media_files",
    indexes = {
      @Index(name = "idx_owner_id", columnList = "owner_id"),
      @Index(name = "idx_s3_key", columnList = "s3_key")
    })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaFile {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private UUID ownerId;

  @Column(nullable = false)
  private String s3Key;

  @Column(nullable = false)
  private String url;

  @Column(nullable = false)
  private String fileType;

  @Column(nullable = false)
  private Long fileSize;

  @Column(nullable = false, updatable = false)
  @Builder.Default
  private LocalDateTime createdAt = LocalDateTime.now();
}
