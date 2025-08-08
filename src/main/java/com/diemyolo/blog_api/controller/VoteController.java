package com.diemyolo.blog_api.controller;

import com.diemyolo.blog_api.model.common.ApiResponse;
import com.diemyolo.blog_api.model.response.vote.VoteResponse;
import com.diemyolo.blog_api.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/votes")
@RestController
public class VoteController {
    @Autowired
    private VoteService voteService;

    @PostMapping("/{postId}/upvote")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<ApiResponse<VoteResponse>> upvotePost(@PathVariable UUID postId) {
        VoteResponse voteResponse = voteService.upvotePost(postId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.created(voteResponse));
    }

    @PostMapping("/{postId}/downvote")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<ApiResponse<VoteResponse>> downvotePost(@PathVariable UUID postId) {
        VoteResponse voteResponse = voteService.downvotePost(postId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.created(voteResponse));
    }
}
