package com.diemyolo.blog_api.service.implementation;

import com.diemyolo.blog_api.entity.Category;
import com.diemyolo.blog_api.entity.Tag;
import com.diemyolo.blog_api.exception.CustomException;
import com.diemyolo.blog_api.model.response.category.CategoryResponse;
import com.diemyolo.blog_api.model.response.tag.TagResponse;
import com.diemyolo.blog_api.repository.TagRepository;
import com.diemyolo.blog_api.service.TagService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private ModelMapper modelMapper;

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<TagResponse> findAllTags() {
        try {
            List<Tag> tags = tagRepository.findAll();
            return modelMapper.map(tags, new TypeToken<List<TagResponse>>() {}.getType());
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}