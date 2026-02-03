package com.diemyolo.admin_service.configuration;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotenvConfig {
  static {
    // Read from environment variable (what Docker sets)
    String profile = System.getenv("SPRING_PROFILES_ACTIVE");
    if (profile == null) {
      profile = "dev";
    }

    String envFileName = ".env." + profile;
    Dotenv dotenv = Dotenv.configure().filename(envFileName).ignoreIfMissing().load();

    dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
  }
}
