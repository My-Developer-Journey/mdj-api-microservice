package com.diemyolo.blog_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "blog_tag_mapping",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"blog_id", "tag_id"})})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogTagMapping {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private UUID blogId;

  @Column(nullable = false)
  private UUID tagId;
}
