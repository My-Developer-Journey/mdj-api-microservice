package com.diemyolo.blog_api.configuration;

import com.diemyolo.blog_api.entity.Post;
import com.diemyolo.blog_api.model.response.post.PostResponse;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModalMapperConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.typeMap(Post.class, PostResponse.class).addMappings(m -> {
            m.map(Post::getAuthor, PostResponse::setAuthor);
            m.map(Post::getCategories, PostResponse::setCategories);
        });

        return mapper;
    }
}
