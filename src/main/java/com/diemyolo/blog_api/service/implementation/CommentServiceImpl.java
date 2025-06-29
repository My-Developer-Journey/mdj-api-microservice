package com.diemyolo.blog_api.service.implementation;

import com.diemyolo.blog_api.entity.User;
import com.diemyolo.blog_api.exception.CustomException;
import com.diemyolo.blog_api.model.response.comment.CommentResponse;
import com.diemyolo.blog_api.mongo.entity.Comment;
import com.diemyolo.blog_api.mongo.repository.CommentRepository;
import com.diemyolo.blog_api.service.AuthenticationService;
import com.diemyolo.blog_api.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    private AuthenticationService authenticationService;

    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public CommentResponse commentOnPost(UUID postId, String content) {
        try {
            User currentUser = authenticationService.findUserByJwt();

            if (content == null || content.trim().isEmpty()) {
                throw new CustomException("Content cannot be empty", HttpStatus.BAD_REQUEST);
            }

            Comment comment = new Comment(UUID.randomUUID(), postId, currentUser.getId(), content, LocalDateTime.now());
            Comment saved = commentRepository.save(comment);
            return modelMapper.map(saved, CommentResponse.class);
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteComment(UUID commentId) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) auth.getPrincipal();

            if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                throw new AccessDeniedException("Only admin can perform this action");
            }
            commentRepository.deleteById(commentId);
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}