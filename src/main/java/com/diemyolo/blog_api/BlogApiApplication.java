package com.diemyolo.blog_api;

import com.diemyolo.blog_api.configuration.EnvLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogApiApplication {

	public static void main(String[] args) {
		EnvLoader.loadEnv();
		SpringApplication.run(BlogApiApplication.class, args);
	}

}
