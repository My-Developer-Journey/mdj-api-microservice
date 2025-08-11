package com.diemyolo.blog_api.configuration;

import com.diemyolo.blog_api.entity.Category;
import com.diemyolo.blog_api.entity.Tag;
import com.diemyolo.blog_api.entity.User;
import com.diemyolo.blog_api.entity.Enumberable.*;
import com.diemyolo.blog_api.repository.CategoryRepository;
import com.diemyolo.blog_api.repository.TagRepository;
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
    public CommandLineRunner seedData(UserRepository userRepository,
                                      CategoryRepository categoryRepository,
                                      TagRepository tagRepository) {
        return args -> {
            // Seed admin
            seedAdmin(userRepository);

            // Seed categories (đầy đủ field)
            Object[][] categories = {
                    {"Front-end", "front-end", "Articles about front-end development", "Front-end Development", "Front-end development tutorials and news", "frontend, html, css, javascript, react"},
                    {"Back-end", "back-end", "Articles about back-end development", "Back-end Development", "Back-end frameworks and tutorials", "backend, java, spring boot, node.js"},
                    {"Security", "security", "Articles about cybersecurity and best practices", "Security", "Cybersecurity tips and news", "security, cybersecurity, hacking, pentest"},
                    {"Devops", "devops", "Articles about DevOps culture and tools", "DevOps", "DevOps best practices and tools", "devops, ci/cd, docker, kubernetes"},
                    {"Project Management", "project-management", "Articles about managing software projects", "Project Management", "Agile, Scrum, and project management tips", "project management, agile, scrum"}
            };
            for (Object[] cat : categories) {
                seedCategoryIfNotExists(categoryRepository, (String) cat[0], (String) cat[1], (String) cat[2], (String) cat[3], (String) cat[4], (String) cat[5]);
            }

            // Seed tags (có slug)
            String[][] tags = {
                    {"React", "react"},
                    {"Next.js", "next-js"},
                    {"TypeScript", "typescript"},
                    {"Tailwind", "tailwind"},
                    {"Node.js", "node-js"}
            };
            for (String[] tag : tags) {
                seedTagIfNotExists(tagRepository, tag[0], tag[1]);
            }
        };
    }

    private void seedAdmin(UserRepository userRepository) {
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
    }

    private void seedCategoryIfNotExists(CategoryRepository categoryRepository, String name, String slug, String description, String seoTitle, String seoDescription, String seoKeywords) {
        if (!categoryRepository.existsBySlug(slug)) {
            Category category = Category.builder()
                    .name(name)
                    .slug(slug)
                    .description(description)
                    .seoTitle(seoTitle)
                    .seoDescription(seoDescription)
                    .seoKeywords(seoKeywords)
                    .build();
            categoryRepository.save(category);
            System.out.println("✅ Seeded category: " + name);
        } else {
            System.out.println("ℹ️ Category already exists: " + name);
        }
    }

    private void seedTagIfNotExists(TagRepository tagRepository, String name, String slug) {
        if (!tagRepository.existsBySlug(slug)) {
            Tag tag = Tag.builder()
                    .name(name)
                    .slug(slug)
                    .build();
            tagRepository.save(tag);
            System.out.println("✅ Seeded tag: " + name);
        } else {
            System.out.println("ℹ️ Tag already exists: " + name);
        }
    }
}