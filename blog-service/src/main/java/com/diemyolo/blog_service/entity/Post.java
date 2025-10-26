package com.diemyolo.blog_service.entity;

import com.diemyolo.blog_service.entity.Enumberable.PostStatus;
import com.fasterxml.jackson.databind.JsonNode;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "posts")
public class Post extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Type(JsonBinaryType.class)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "contentJson", columnDefinition = "jsonb")
    private JsonNode contentJson;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "thumbnail_s3_key")
    private String thumbnailS3Key;

    // SEO metadata
    @Column(name = "seo_title")
    private String seoTitle;

    @Column(name = "seo_description", length = 300)
    private String seoDescription;

    @Column(name = "seo_keywords")
    private String seoKeywords;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToMany(fetch = FetchType.LAZY)
    @OrderColumn(name = "category_order")
    @JoinTable(
            name = "post_categories",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @Builder.Default
    private List<Category> categories = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @OrderColumn(name = "tag_order")
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @Builder.Default
    private List<Tag> tags = new ArrayList<>();

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private PostStatus postStatus;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @Column(name = "accepted_at")
    private LocalDateTime acceptedAt;

    @Column(name = "rejected_at")
    private LocalDateTime rejectedAt;

    @Column(name = "rejected_note")
    private String rejectedNote;

    @Column(name = "scheduled_publish_date")
    private LocalDateTime scheduledPublishDate;

    @Column(name = "view_count")
    @Builder.Default
    private long viewCount = 0;
}
