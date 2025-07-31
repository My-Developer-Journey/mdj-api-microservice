package com.diemyolo.blog_api.configuration;

import com.diemyolo.blog_api.entity.Category;
import com.diemyolo.blog_api.entity.User;
import com.diemyolo.blog_api.entity.Enumberable.*;
import com.diemyolo.blog_api.repository.CategoryRepository;
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
    public CommandLineRunner seedAdminAndCategories(UserRepository userRepository, CategoryRepository categoryRepository) {
        return args -> {
            // Seed admin user
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
                System.out.println("✅ Seeded admin user: " + adminEmail);
            } else {
                System.out.println("ℹ️ Admin user already exists: " + adminEmail);
            }

            // Seed categories
            seedCategoryIfNotExists(categoryRepository, "Technology", "technology", "Tech trends and tutorials", "Latest in tech", "technology, software, coding");
            seedCategoryIfNotExists(categoryRepository, "Life", "life", "Life stories and insights", "Explore life", "life, personal, experience");
            seedCategoryIfNotExists(categoryRepository, "Tutorials", "tutorials", "Step-by-step tutorials", "Learn with tutorials", "guide, how-to, learning");
            seedCategoryIfNotExists(categoryRepository, "Personal Growth", "personal-growth", "Mindset and self-development", "Grow yourself", "motivation, growth, productivity");
        };
    }

    private void seedCategoryIfNotExists(CategoryRepository categoryRepository, String name, String slug, String description, String seoTitle, String seoKeywords) {
        if (!categoryRepository.existsBySlug(slug)) {
            Category category = Category.builder()
                    .name(name)
                    .slug(slug)
                    .description(description)
                    .seoTitle(seoTitle)
                    .seoDescription(description)
                    .seoKeywords(seoKeywords)
                    .build();
            categoryRepository.save(category);
            System.out.println("✅ Seeded category: " + name);
        } else {
            System.out.println("ℹ️ Category already exists: " + name);
        }
    }

}
