package com.diemyolo.blog_api.model.request.category;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    @NotBlank(message = "The name is required.")
    private String name;

    @NotBlank(message = "The slug is required.")
    private String slug;

    @NotBlank(message = "The description is required.")
    private String description;

    private String seoTitle;
    private String seoDescription;
    private String seoKeywords;
}
