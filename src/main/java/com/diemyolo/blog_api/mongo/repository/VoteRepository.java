package com.diemyolo.blog_api.mongo.repository;

import com.diemyolo.blog_api.mongo.entity.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VoteRepository extends MongoRepository<Vote, String> {
    List<Vote> findByPostId(UUID postId);
    List<Vote> findByUserId(UUID userId);
    Optional<Vote> findByPostIdAndUserId(UUID postId, UUID userId);
}