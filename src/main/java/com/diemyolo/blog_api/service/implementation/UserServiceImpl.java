package com.diemyolo.blog_api.service.implementation;

import com.diemyolo.blog_api.entity.Enumberable.Role;
import com.diemyolo.blog_api.entity.Enumberable.Status;
import com.diemyolo.blog_api.entity.User;
import com.diemyolo.blog_api.exception.CustomException;
import com.diemyolo.blog_api.model.request.user.UserRequest;
import com.diemyolo.blog_api.model.response.like.LikeResponse;
import com.diemyolo.blog_api.model.response.user.UserResponse;
import com.diemyolo.blog_api.mongo.entity.Like;
import com.diemyolo.blog_api.repository.UserRepository;
import com.diemyolo.blog_api.service.AWSS3Service;
import com.diemyolo.blog_api.service.AuthenticationService;
import com.diemyolo.blog_api.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    private static final String PHONE_NUMBER_REGEX = "^\\d{10}$";

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AWSS3Service awsS3Service;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserResponse getCurrentUser() {
        try {
            User currentUser = authenticationService.findUserByJwt();
            return modelMapper.map(currentUser, UserResponse.class);
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public UserResponse updateUser(UserRequest request){
        try{
            Pattern phonePattern = Pattern.compile(PHONE_NUMBER_REGEX);
            Matcher phoneMatcher = phonePattern.matcher(request.getPhoneNumber());
            User currentUser = authenticationService.findUserByJwt();

            if (!currentUser.getEmail().equals(request.getEmail())) {
                throw new CustomException("You are not authorized to update this user.", HttpStatus.FORBIDDEN);
            }

            if (!phoneMatcher.matches()) {
                throw new CustomException("Invalid phone number.", HttpStatus.BAD_REQUEST);
            }

            currentUser.setDisplayName(request.getDisplayName());
            currentUser.setBio(request.getBio());
            currentUser.setFacebookUrl(request.getFacebookUrl());
            currentUser.setGithubUrl(request.getGithubUrl());
            currentUser.setGender(request.getGender());

            return modelMapper.map(userRepository.save(currentUser), UserResponse.class);
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public UserResponse updateUserStatus(UUID userId){
        try{
            User currentUser = authenticationService.findUserByJwt();
            if(currentUser.getRole() != Role.ADMIN){
                throw new CustomException("You do not have permission to perform this action.", HttpStatus.FORBIDDEN);
            }

            User updatedUser = userRepository.findById(userId)
                    .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

            updatedUser.setStatus(
                    updatedUser.getStatus() == Status.ACTIVE ? Status.INACTIVE : Status.ACTIVE
            );

            return modelMapper.map(userRepository.save(currentUser), UserResponse.class);
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public UserResponse getUserById(UUID userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
            return modelMapper.map(user, UserResponse.class);
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public Page<UserResponse> getAllUsers(int page, int size, String sortBy, String sortDir) {
        try {
            Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            Pageable pageable = PageRequest.of(page, size, sort);
            Page<User> usersPage = userRepository.findAll(pageable);

            return usersPage.map(user -> modelMapper.map(user, UserResponse.class));
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public UserResponse updateUserAvatar(MultipartFile file, String userEmail){
        try{
            User currentUser = authenticationService.findUserByJwt();

            if (!currentUser.getEmail().equals(userEmail)) {
                throw new CustomException("You are not authorized to update this user.", HttpStatus.FORBIDDEN);
            }

            Map<String, String> result = awsS3Service.uploadFile(file);
            String url = result.get("url");
            String key = result.get("key");

            currentUser.setAvatar(url);
            currentUser.setAvatarS3Key(key);

            return modelMapper.map(userRepository.save(currentUser), UserResponse.class);
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}