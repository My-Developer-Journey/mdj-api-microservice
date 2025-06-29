package com.diemyolo.blog_api.mongo.repository;

import com.diemyolo.blog_api.mongo.entity.Like;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LikeRepository extends MongoRepository<Like, String> {
    List<Like> findByPostId(UUID postId);
    List<Like> findByUserId(UUID userId);
    List<Like> findByPostIdAndUserId(UUID postId, UUID userId);
}