package com.diemyolo.blog_api.model.request.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    @NotBlank(message = "The title is required.")
    private String title;

    @NotBlank(message = "The slug is required.")
    private String slug;

    @NotBlank(message = "The content is required.")
    private String content;

    private String seoTitle;
    private String seoDescription;
    private String seoKeywords;

    @NotNull(message = "Author ID is required.")
    private UUID authorId;

    private List<UUID> categoryIds;

    private boolean published;
}