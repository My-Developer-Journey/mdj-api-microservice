package com.diemyolo.blog_api.service.implementation;

import com.diemyolo.blog_api.entity.User;
import com.diemyolo.blog_api.exception.CustomException;
import com.diemyolo.blog_api.model.response.follow.FollowResponse;
import com.diemyolo.blog_api.mongo.entity.Follow;
import com.diemyolo.blog_api.mongo.repository.FollowRepository;
import com.diemyolo.blog_api.repository.UserRepository;
import com.diemyolo.blog_api.service.AuthenticationService;
import com.diemyolo.blog_api.service.FollowService;
import com.diemyolo.blog_api.service.JWTService;
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
public class FollowServiceImpl implements FollowService {
    private final FollowRepository followRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    public FollowServiceImpl(FollowRepository followRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.followRepository = followRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public FollowResponse followUser(UUID followingId) {
        try {
            User currentUser = authenticationService.findUserByJwt();

            userRepository.findById(followingId)
                    .orElseThrow(() -> new BadCredentialsException("Following id not found!"));

            if (currentUser.getId().equals(followingId)) {
                throw new CustomException("Cannot follow yourself!", HttpStatus.BAD_REQUEST);
            }

            if (!followRepository.findByFollowerIdAndFollowingId(currentUser.getId(), followingId).isEmpty()) {
                throw new CustomException("Already followed!", HttpStatus.BAD_REQUEST);
            }

            Follow follow = new Follow(UUID.randomUUID(), currentUser.getId(), followingId, LocalDateTime.now());
            Follow saved = followRepository.save(follow);
            return modelMapper.map(saved, FollowResponse.class);
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void unfollowUser(UUID followingId) {
        try {
            User currentUser = authenticationService.findUserByJwt();

            if (currentUser.getId().equals(followingId)) {
                throw new CustomException("Cannot unfollow yourself!", HttpStatus.BAD_REQUEST);
            }

            userRepository.findById(followingId)
                    .orElseThrow(() -> new BadCredentialsException("Following id not found!"));

            List<Follow> existingFollows = followRepository.findByFollowerIdAndFollowingId(currentUser.getId(), followingId);

            if (existingFollows.isEmpty()) {
                throw new CustomException("You are not following this user!", HttpStatus.BAD_REQUEST);
            }

            followRepository.deleteAll(existingFollows);
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public List<FollowResponse> getFollowers() {
        try {
            User currentUser = authenticationService.findUserByJwt();

            userRepository.findById(currentUser.getId())
                    .orElseThrow(() -> new BadCredentialsException("User id not found!"));

            return followRepository.findByFollowingId(currentUser.getId())
                    .stream()
                    .map(f -> modelMapper.map(f, FollowResponse.class))
                    .toList();
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public List<FollowResponse> getFollowing() {
        try {
            User currentUser = authenticationService.findUserByJwt();

            userRepository.findById(currentUser.getId())
                    .orElseThrow(() -> new BadCredentialsException("User id not found!"));

            return followRepository.findByFollowerId(currentUser.getId())
                    .stream()
                    .map(f -> modelMapper.map(f, FollowResponse.class))
                    .toList();
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}