package com.diemyolo.blog_service.controller;

import com.diemyolo.blog_service.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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