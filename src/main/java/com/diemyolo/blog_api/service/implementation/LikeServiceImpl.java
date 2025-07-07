package com.diemyolo.blog_api.service.implementation;

import com.diemyolo.blog_api.entity.User;
import com.diemyolo.blog_api.exception.CustomException;
import com.diemyolo.blog_api.model.response.like.LikeResponse;
import com.diemyolo.blog_api.mongo.entity.Like;
import com.diemyolo.blog_api.mongo.repository.LikeRepository;
import com.diemyolo.blog_api.repository.PostRepository;
import com.diemyolo.blog_api.service.AuthenticationService;
import com.diemyolo.blog_api.service.LikeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Autowired
    private AuthenticationService authenticationService;

    public LikeServiceImpl(LikeRepository likeRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public LikeResponse likePost(UUID postId) {
        try {
            postRepository.findById(postId)
                    .orElseThrow(() -> new BadCredentialsException("Post id not found!"));

            User currentUser = authenticationService.findUserByJwt();

            if (!likeRepository.findByPostIdAndUserId(postId, currentUser.getId()).isEmpty()) {
                throw new CustomException("Already liked!", HttpStatus.BAD_REQUEST);
            }

            Like like = new Like(UUID.randomUUID(), postId, currentUser.getId(), LocalDateTime.now());
            Like saved = likeRepository.save(like);
            return modelMapper.map(saved, LikeResponse.class);
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void unlikePost(UUID postId) {
        try {
            postRepository.findById(postId)
                    .orElseThrow(() -> new BadCredentialsException("Post id not found!"));

            User currentUser = authenticationService.findUserByJwt();

            List<Like> existingLikes = likeRepository.findByPostIdAndUserId(postId, currentUser.getId());

            if (existingLikes.isEmpty()) {
                throw new CustomException("You have not liked this post!", HttpStatus.BAD_REQUEST);
            }

            likeRepository.deleteAll(existingLikes);
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
