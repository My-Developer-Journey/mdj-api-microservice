package com.diemyolo.blog_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(length = 300)
    private String description;

    @Column(name = "seo_title")
    private String seoTitle;

    @Column(name = "seo_description", length = 300)
    private String seoDescription;

    @Column(name = "seo_keywords")
    private String seoKeywords;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts;
}
