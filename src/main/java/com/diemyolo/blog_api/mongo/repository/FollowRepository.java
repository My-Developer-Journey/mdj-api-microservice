package com.diemyolo.blog_api.mongo.repository;

import com.diemyolo.blog_api.mongo.entity.Follow;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FollowRepository extends MongoRepository<Follow, UUID> {
    List<Follow> findByFollowerId(UUID followerId);
    List<Follow> findByFollowingId(UUID followingId);
    List<Follow> findByFollowerIdAndFollowingId(UUID followerId, UUID followingId);
}
