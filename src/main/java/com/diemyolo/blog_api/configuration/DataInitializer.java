package com.diemyolo.blog_api.configuration;

import com.diemyolo.blog_api.entity.User;
import com.diemyolo.blog_api.entity.Enumberable.*;
import com.diemyolo.blog_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner seedAdmin(UserRepository userRepository) {
        return args -> {
            String adminEmail = "admin@blogapi.com";

            if (!userRepository.existsByEmail(adminEmail)) {
                User admin = User.builder()
                        .email(adminEmail)
                        .displayName("Admin")
                        .phoneNumber("0123456789")
                        .gender(Gender.MALE)
                        .role(Role.ADMIN)
                        .status(Status.ACTIVE)
                        .provider(Provider.LOCAL)
                        .enabled(true)
                        .password(passwordEncoder.encode("123456"))
                        .build();

                userRepository.save(admin);
                System.out.println("Seeded admin user: " + adminEmail);
            } else {
                System.out.println("Admin user already exists: " + adminEmail);
            }
        };
    }
}
