package com.diemyolo.blog_service.repository;

import com.diemyolo.blog_service.entity.Enumberable.PostStatus;
import com.diemyolo.blog_service.entity.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    boolean existsBySlug(String slug);
    boolean existsByTitle(String title);
    List<Post> findByPostStatusAndScheduledPublishDateBetween(PostStatus postStatus,
                                                              LocalDateTime start,
                                                              LocalDateTime end);
    boolean existsByAuthorIdAndPostStatus(UUID authorId, PostStatus postStatus);
    Optional<Post> findFirstByAuthorIdAndPostStatus(UUID authorId, PostStatus postStatus);
    List<Post> findByAuthorId(UUID authorId, Sort sort);
    Optional<Post> findBySlug(String slug);
    List<Post> findByPostStatus(PostStatus postStatus);
}
