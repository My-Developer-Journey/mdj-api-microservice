package com.diemyolo.blog_api.controller;

import com.diemyolo.blog_api.model.common.ApiResponse;
import com.diemyolo.blog_api.model.response.tag.TagResponse;
import com.diemyolo.blog_api.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/tags")
@RestController
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<TagResponse>>> findAllTags() {
        List<TagResponse> tag = tagService.findAllTags();
        return ResponseEntity
                .ok(ApiResponse.success("Tag list successfully fetched!", tag));
    }
}
