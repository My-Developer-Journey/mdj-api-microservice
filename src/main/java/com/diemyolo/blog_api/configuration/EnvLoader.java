package com.diemyolo.blog_api.configuration;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvLoader {
    public static void loadEnv() {
        Dotenv dotenv = Dotenv.configure().load();
        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });
    }
}
