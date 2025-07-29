package com.diemyolo.blog_api.model.response.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostUserResponse {
    private UUID id;
    private String email;
    private String displayName;
    private @Nullable String avatar;
}
