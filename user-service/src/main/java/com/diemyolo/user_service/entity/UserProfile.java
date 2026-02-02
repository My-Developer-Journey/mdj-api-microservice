package com.diemyolo.user_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {

  @Id private UUID userId;

  @Column(nullable = false)
  private String fullName;

  @Column(nullable = true)
  private String avatarUrl;

  @Column(nullable = true, length = 500)
  private String bio;
}
