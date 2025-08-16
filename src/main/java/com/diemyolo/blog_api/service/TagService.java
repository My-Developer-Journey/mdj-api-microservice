package com.diemyolo.blog_api.service;

import com.diemyolo.blog_api.entity.Tag;
import com.diemyolo.blog_api.model.response.category.CategoryResponse;
import com.diemyolo.blog_api.model.response.tag.TagResponse;

import java.util.List;

public interface TagService {
    List<TagResponse> findAllTags();
}