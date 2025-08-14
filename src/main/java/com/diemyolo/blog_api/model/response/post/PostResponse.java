package com.diemyolo.blog_api.model.response.post;

import com.diemyolo.blog_api.entity.Enumberable.PostStatus;
import com.diemyolo.blog_api.model.response.user.UserResponse;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    private String thumbnailUrl;
    private String thumbnailS3Key;
    private String content;
    private String seoTitle;
    private String seoDescription;
    private String seoKeywords;
    private PostUserResponse author;
    private List<PostCategoryResponse> categories;
    private List<PostTagResponse> tags;
    private PostStatus postStatus;
    private LocalDateTime submittedAt;
    private LocalDateTime rejectedAt;
    private String rejectedNote;
    private LocalDateTime scheduledPublishDate;
    @Builder.Default
    private long viewCount = 0;
}