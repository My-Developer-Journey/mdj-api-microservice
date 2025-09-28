package com.diemyolo.blog_api.controller;

import com.diemyolo.blog_api.model.common.ApiResponse;
import com.diemyolo.blog_api.model.response.post.PostResponse;
import com.diemyolo.blog_api.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("")
@RestController
public class TestController {

    @GetMapping("/")
    public String testDeploy() {
        return "Deploy thành công!";
    }

    @Autowired
    private PostService postService;
}