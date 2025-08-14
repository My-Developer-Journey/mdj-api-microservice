package com.diemyolo.blog_api.model.request.post;

import com.diemyolo.blog_api.entity.Enumberable.PostStatus;
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
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    @NotBlank(message = "The title is required.")
    private String title;

    @NotBlank(message = "The content is required.")
    private String content;

    @NotNull(message = "Author ID is required.")
    private UUID authorId;

    private List<UUID> categoryIds;

    private List<UUID> tagIds;

    private PostStatus postStatus;

    @NotNull(message = "Publish Date is required.")
    private LocalDateTime scheduledPublishDate;
}