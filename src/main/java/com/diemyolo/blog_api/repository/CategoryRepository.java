package com.diemyolo.blog_api.repository;

import com.diemyolo.blog_api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    boolean existsByNameIgnoreCase(String name);
    boolean existsBySlugIgnoreCase(String slug);
    boolean existsBySlug(String slug);
}