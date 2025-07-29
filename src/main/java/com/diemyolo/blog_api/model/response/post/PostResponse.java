package com.diemyolo.blog_api.model.response.post;

import com.diemyolo.blog_api.model.response.user.UserResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse {
    private UUID id;
    private String title;
    private String slug;
    private String content;
    private String seoTitle;
    private String seoDescription;
    private String seoKeywords;
    private PostUserResponse author;
    private List<PostCategoryResponse> categories;
    private boolean published;
}