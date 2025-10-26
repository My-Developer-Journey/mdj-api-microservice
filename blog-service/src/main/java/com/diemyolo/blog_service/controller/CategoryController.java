package com.diemyolo.blog_service.controller;

import com.diemyolo.blog_service.model.common.ApiResponse;
import com.diemyolo.blog_service.model.request.category.CategoryRequest;
import com.diemyolo.blog_service.model.response.category.CategoryResponse;
import com.diemyolo.blog_service.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/categories")
@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<ApiResponse<CategoryResponse>> addCategory(@Valid @RequestBody CategoryRequest request) {
        CategoryResponse category = categoryService.addCategory(request);
        return ResponseEntity
                .ok(ApiResponse.success("Add category successfully!", category));
    }

    @PutMapping()
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(@RequestParam("id") UUID id, @Valid @RequestBody CategoryRequest request) {
        CategoryResponse category = categoryService.updateCategory(id, request);
        return ResponseEntity
                .ok(ApiResponse.success("Update category successfully!", category));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> findAllCategories() {
        List<CategoryResponse> category = categoryService.findAllCategories();
        return ResponseEntity
                .ok(ApiResponse.success("Category list successfully fetched!", category));
    }
}