package com.diemyolo.blog_service.configuration;

import com.diemyolo.blog_service.entity.Post;
import com.diemyolo.blog_service.entity.User;
import com.diemyolo.blog_service.model.response.post.PostCategoryResponse;
import com.diemyolo.blog_service.model.response.post.PostResponse;
import com.diemyolo.blog_service.model.response.post.PostTagResponse;
import com.diemyolo.blog_service.model.response.post.PostUserResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        TypeMap<Post, PostResponse> typeMap = mapper.createTypeMap(Post.class, PostResponse.class);

        typeMap.addMappings(m -> {
            m.map(Post::getId, PostResponse::setId);
            m.map(Post::getTitle, PostResponse::setTitle);
            m.map(Post::getSlug, PostResponse::setSlug);
            m.map(Post::getThumbnailUrl, PostResponse::setThumbnailUrl);
            m.map(Post::getThumbnailS3Key, PostResponse::setThumbnailS3Key);
            m.map(Post::getContent, PostResponse::setContent);
            m.map(Post::getContentJson, PostResponse::setContentJson);
            m.map(Post::getSeoTitle, PostResponse::setSeoTitle);
            m.map(Post::getSeoDescription, PostResponse::setSeoDescription);
            m.map(Post::getSeoKeywords, PostResponse::setSeoKeywords);
            m.map(Post::getPostStatus, PostResponse::setPostStatus);
            m.map(Post::getSubmittedAt, PostResponse::setSubmittedAt);
            m.map(Post::getRejectedAt, PostResponse::setRejectedAt);
            m.map(Post::getRejectedNote, PostResponse::setRejectedNote);
            m.map(Post::getScheduledPublishDate, PostResponse::setScheduledPublishDate);
            m.map(Post::getViewCount, PostResponse::setViewCount);
        });

        // Custom mapping for author
        typeMap.setPostConverter(context -> {
            Post source = context.getSource();
            PostResponse destination = context.getDestination();

            User author = source.getAuthor();
            if (author != null) {
                PostUserResponse authorDTO = PostUserResponse.builder()
                        .id(author.getId())
                        .email(author.getEmail())
                        .displayName(author.getDisplayName())
                        .avatar(author.getAvatar())
                        .build();
                destination.setAuthor(authorDTO);
            }

            if (source.getCategories() != null) {
                destination.setCategories(source.getCategories().stream().map(cat ->
                        PostCategoryResponse.builder()
                                .id(cat.getId())
                                .name(cat.getName())
                                .build()
                ).collect(Collectors.toList()));
            }

            if (source.getTags() != null) {
                destination.setTags(source.getTags().stream().map(tag ->
                        PostTagResponse.builder()
                                .id(tag.getId())
                                .name(tag.getName())
                                .build()
                ).collect(Collectors.toList()));
            }

            return destination;
        });

        return mapper;
    }
}
