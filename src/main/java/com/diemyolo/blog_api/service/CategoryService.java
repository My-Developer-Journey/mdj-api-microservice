package com.diemyolo.blog_api.service;

import com.diemyolo.blog_api.model.request.category.CategoryRequest;
import com.diemyolo.blog_api.model.response.category.CategoryResponse;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryResponse addCategory(CategoryRequest request);

    CategoryResponse updateCategory(UUID id, CategoryRequest request);

    List<CategoryResponse> findAllCategories();
}
