package com.diemyolo.blog_api.repository;

import com.diemyolo.blog_api.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {
    boolean existsByName(String name);
    boolean existsBySlug(String slug);
}