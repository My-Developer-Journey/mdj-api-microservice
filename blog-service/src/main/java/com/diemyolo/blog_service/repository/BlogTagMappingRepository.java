package com.diemyolo.blog_service.repository;

import com.diemyolo.blog_service.entity.BlogTagMapping;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogTagMappingRepository extends JpaRepository<BlogTagMapping, UUID> {}
