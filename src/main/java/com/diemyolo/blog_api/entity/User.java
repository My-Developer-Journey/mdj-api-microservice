package com.diemyolo.blog_api.entity;

import com.diemyolo.blog_api.entity.Enumberable.Gender;
import com.diemyolo.blog_api.entity.Enumberable.Provider;
import com.diemyolo.blog_api.entity.Enumberable.Role;
import com.diemyolo.blog_api.entity.Enumberable.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "\"users\"")
public class User extends BaseEntity implements UserDetails {
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "avatar", nullable = true)
    private String avatar;

    @Column(name = "avatar_s3_key", nullable = true)
    private String avatarS3Key;

    @Column(name = "verification_code", length = 64)
    private String verificationCode;

    @Column(name = "verification_code_created_at")
    private LocalDateTime verificationCodeCreatedAt;

    @Column(name = "enabled", nullable = false)
    @Builder.Default
    private boolean enabled = false;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

    @Column(name = "facebook_url")
    private String facebookUrl;

    @Column(name = "github_url")
    private String githubUrl;

    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @Enumerated(EnumType.ORDINAL)
    private Role role;

    @Enumerated(EnumType.ORDINAL)
    private Provider provider;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Post> posts;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roleNameWithPrefix = "ROLE_" + role.name();
        return List.of(new SimpleGrantedAuthority(roleNameWithPrefix));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
