package com.diemyolo.blog_service.service;

import com.diemyolo.blog_service.model.request.category.CategoryRequest;
import com.diemyolo.blog_service.model.response.category.CategoryResponse;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryResponse addCategory(CategoryRequest request);

    CategoryResponse updateCategory(UUID id, CategoryRequest request);

    List<CategoryResponse> findAllCategories();
}
