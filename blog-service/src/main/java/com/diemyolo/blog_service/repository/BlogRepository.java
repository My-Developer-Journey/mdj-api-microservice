package com.diemyolo.blog_service.repository;

import com.diemyolo.blog_service.entity.Blog;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Blog, UUID> {}
