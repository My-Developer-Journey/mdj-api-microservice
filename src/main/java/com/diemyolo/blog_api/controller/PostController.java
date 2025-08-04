package com.diemyolo.blog_api.controller;

import com.diemyolo.blog_api.model.common.ApiResponse;
import com.diemyolo.blog_api.model.request.authentication.SignInRequest;
import com.diemyolo.blog_api.model.request.post.PostRequest;
import com.diemyolo.blog_api.model.response.post.PostResponse;
import com.diemyolo.blog_api.service.PostService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/posts")
@RestController
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping()
    public ResponseEntity<ApiResponse<Object>> addPost(
            @Valid @RequestBody PostRequest request
    ) {
        PostResponse response = postService.addPost(request);

        return ResponseEntity.ok(ApiResponse.success("Add post successfully, please wait for admin verification!", response));
    }
}
