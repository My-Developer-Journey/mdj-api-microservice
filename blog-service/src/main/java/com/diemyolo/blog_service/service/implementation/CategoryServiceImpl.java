package com.diemyolo.blog_service.service.implementation;

import com.diemyolo.blog_service.entity.Category;
import com.diemyolo.blog_service.exception.CustomException;
import com.diemyolo.blog_service.model.request.category.CategoryRequest;
import com.diemyolo.blog_service.model.response.category.CategoryResponse;
import com.diemyolo.blog_service.repository.CategoryRepository;
import com.diemyolo.blog_service.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private ModelMapper modelMapper;

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public CategoryResponse addCategory(CategoryRequest request){
        try{
            if (categoryRepository.existsByNameIgnoreCase(request.getName())) {
                throw new CustomException("Category name already exists.", HttpStatus.BAD_REQUEST);
            }

            if (categoryRepository.existsBySlugIgnoreCase(request.getSlug())) {
                throw new CustomException("Category slug already exists.", HttpStatus.BAD_REQUEST);
            }

            Category category = modelMapper.map(request, Category.class);
            Category savedCategory = categoryRepository.save(category);

            return modelMapper.map(savedCategory, CategoryResponse.class);
        } catch (
        CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(UUID id, CategoryRequest request) {
        try {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new CustomException("Category not found", HttpStatus.NOT_FOUND));

            if (!category.getName().equalsIgnoreCase(request.getName())
                    && categoryRepository.existsByNameIgnoreCase(request.getName())) {
                throw new CustomException("Category name already exists", HttpStatus.BAD_REQUEST);
            }

            if (!category.getSlug().equalsIgnoreCase(request.getSlug())
                    && categoryRepository.existsBySlugIgnoreCase(request.getSlug())) {
                throw new CustomException("Category slug already exists", HttpStatus.BAD_REQUEST);
            }

            modelMapper.map(request, category);

            Category updated = categoryRepository.save(category);
            return modelMapper.map(updated, CategoryResponse.class);

        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public List<CategoryResponse> findAllCategories() {
        try {
            List<Category> categories = categoryRepository.findAll();
            return modelMapper.map(categories, new TypeToken<List<CategoryResponse>>() {}.getType());
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
