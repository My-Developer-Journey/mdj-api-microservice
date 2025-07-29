package com.diemyolo.blog_api.model.response.user;

import com.diemyolo.blog_api.entity.Enumberable.Gender;
import com.diemyolo.blog_api.entity.Enumberable.Role;
import com.diemyolo.blog_api.entity.Enumberable.Status;
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
public class UserResponse {
    private UUID id;
    private String email;
    private String displayName;
    private String phoneNumber;
    private @Nullable String avatar;
    private @Nullable String avatarS3Key;
    private @Nullable String bio;
    private @Nullable String facebookUrl;
    private @Nullable String githubUrl;
    private Gender gender;
    private Status status;
    private Role role;
}
