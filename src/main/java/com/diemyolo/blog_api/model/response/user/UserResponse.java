package com.diemyolo.blog_api.model.response.user;

import com.diemyolo.blog_api.entity.Enumberable.Gender;
import com.diemyolo.blog_api.entity.Enumberable.Role;
import com.diemyolo.blog_api.entity.Enumberable.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private String email;
    private String displayName;
    private String phoneNumber;
    private @Nullable String avatar;
    private @Nullable String bio;
    private @Nullable String facebookUrl;
    private @Nullable String githubUrl;
    private Gender gender;
    private Status status;
    private Role role;
}
