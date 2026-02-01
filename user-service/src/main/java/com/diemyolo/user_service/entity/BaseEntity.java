package com.diemyolo.user_service.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Data
@MappedSuperclass
public abstract class BaseEntity {

  @Id @UuidGenerator private UUID id;

  @Column(name = "created_date", nullable = false, updatable = false)
  private LocalDateTime createdDate;

  @Column(name = "updated_date")
  private LocalDateTime updatedDate;

  @Column(name = "is_deleted", nullable = false)
  private boolean isDeleted;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @PrePersist
  protected void onCreate() {
    LocalDateTime now = LocalDateTime.now();
    this.createdDate = now;
    this.updatedDate = now;
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedDate = LocalDateTime.now();
  }
}
