package com.diemyolo.blog_service.service;

import com.diemyolo.blog_service.model.response.tag.TagResponse;

import java.util.List;

public interface TagService {
    List<TagResponse> findAllTags();
}