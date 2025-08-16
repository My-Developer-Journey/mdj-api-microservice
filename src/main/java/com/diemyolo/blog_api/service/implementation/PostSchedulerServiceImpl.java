package com.diemyolo.blog_api.service.implementation;

import com.diemyolo.blog_api.entity.Enumberable.PostStatus;
import com.diemyolo.blog_api.entity.Post;
import com.diemyolo.blog_api.exception.CustomException;
import com.diemyolo.blog_api.repository.PostRepository;
import com.diemyolo.blog_api.service.PostSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class PostSchedulerServiceImpl implements PostSchedulerService {
    @Autowired
    private PostRepository postRepository;

    @Transactional
    @Override
    @Scheduled(cron = "0 0 0 * * ?") // chạy lúc 00:00 hàng ngày
    public void publishAcceptedPostsForToday() {
        try {
            LocalDate today = LocalDate.now();

            List<Post> posts = postRepository.findByPostStatusAndScheduledPublishDateBetween(
                    PostStatus.ACCEPTED,
                    today.atStartOfDay(),
                    today.atTime(LocalTime.MAX) // 23:59:59.999999999
            );

            for (Post post : posts) {
                post.setPostStatus(PostStatus.PUBLISHED);
            }

            if (!posts.isEmpty()) {
                postRepository.saveAll(posts);
                System.out.println("✅ Published " + posts.size() + " posts at: " + LocalDateTime.now());
            } else {
                System.out.println("ℹ️ No posts to publish for today at: " + LocalDateTime.now());
            }
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
