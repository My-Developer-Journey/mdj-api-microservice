package com.diemyolo.blog_api.service.implementation;

import com.diemyolo.blog_api.entity.User;
import com.diemyolo.blog_api.exception.CustomException;
import com.diemyolo.blog_api.model.response.vote.VoteResponse;
import com.diemyolo.blog_api.mongo.entity.Vote;
import com.diemyolo.blog_api.mongo.repository.VoteRepository;
import com.diemyolo.blog_api.repository.PostRepository;
import com.diemyolo.blog_api.service.AuthenticationService;
import com.diemyolo.blog_api.service.VoteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoteServiceImpl implements VoteService {
    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Autowired
    private AuthenticationService authenticationService;

    public VoteServiceImpl(VoteRepository voteRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.voteRepository = voteRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public VoteResponse upvotePost(UUID postId) {
        try {
            postRepository.findById(postId)
                    .orElseThrow(() -> new BadCredentialsException("Post id not found!"));

            User currentUser = authenticationService.findUserByJwt();

            // Tìm vote hiện có
            Optional<Vote> existingVoteOpt = voteRepository.findByPostIdAndUserId(postId, currentUser.getId());

            Vote vote;
            if (existingVoteOpt.isEmpty()) {
                // Chưa có -> tạo mới
                vote = new Vote(UUID.randomUUID(), postId, currentUser.getId(),
                        true, false, LocalDateTime.now());
            } else {
                // Có rồi -> toggle
                vote = existingVoteOpt.get();
                vote.setUpvote(!vote.isUpvote()); // đảo giá trị
                if (vote.isUpvote()) {
                    vote.setDownvote(false); // nếu upvote thì bỏ downvote
                }
            }

            Vote saved = voteRepository.save(vote);
            return modelMapper.map(saved, VoteResponse.class);

        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public VoteResponse downvotePost(UUID postId) {
        try {
            postRepository.findById(postId)
                    .orElseThrow(() -> new BadCredentialsException("Post id not found!"));

            User currentUser = authenticationService.findUserByJwt();

            Optional<Vote> existingVoteOpt = voteRepository.findByPostIdAndUserId(postId, currentUser.getId());

            Vote vote;
            if (existingVoteOpt.isEmpty()) {
                // Chưa có -> tạo mới
                vote = new Vote(UUID.randomUUID(), postId, currentUser.getId(),
                        false, true, LocalDateTime.now());
            } else {
                // Có rồi -> toggle
                vote = existingVoteOpt.get();
                vote.setDownvote(!vote.isDownvote()); // đảo giá trị
                if (vote.isDownvote()) {
                    vote.setUpvote(false); // nếu downvote thì bỏ upvote
                }
            }

            Vote saved = voteRepository.save(vote);
            return modelMapper.map(saved, VoteResponse.class);

        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
