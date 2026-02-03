package com.diemyolo.blog_service.repository;

import com.diemyolo.blog_service.entity.BlogTag;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogTagRepository extends JpaRepository<BlogTag, UUID> {
  Optional<BlogTag> findByName(String name);
}
