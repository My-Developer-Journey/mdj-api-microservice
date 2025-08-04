package com.diemyolo.blog_api.repository;

import com.diemyolo.blog_api.entity.Post;
import com.diemyolo.blog_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    boolean existsBySlug(String slug);
    boolean existsByTitle(String title);
}
