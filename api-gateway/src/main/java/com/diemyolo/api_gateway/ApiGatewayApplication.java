package com.diemyolo.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class ApiGatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(ApiGatewayApplication.class, args);
  }

  // 1) Rate limiter bean (token bucket)
  @Bean
  public RedisRateLimiter redisRateLimiter() {
    // replenishRate: token/giây, burstCapacity: max token
    return new RedisRateLimiter(1, 1);
  }

  // 2) KeyResolver: limit theo IP (bạn có thể đổi theo userId, apiKey...)
  @Bean
  public KeyResolver ipKeyResolver() {
    return exchange ->
        Mono.just(
            exchange.getRequest().getRemoteAddress() != null
                ? exchange.getRequest().getRemoteAddress().getAddress().getHostAddress()
                : "unknown");
  }

  // 3) RouteLocator: dùng requestRateLimiter (KHÔNG dùng throttle.apply nữa)
  @Bean
  public RouteLocator customRouteLocator(
      RouteLocatorBuilder builder, RedisRateLimiter rateLimiter, KeyResolver ipKeyResolver) {
    return builder
        .routes()
        // Blog Service routes
        .route(
            r ->
                r.path("/api/v1/blogs/**")
                    .filters(
                        f ->
                            f.requestRateLimiter(
                                config ->
                                    config
                                        .setRateLimiter(rateLimiter)
                                        .setKeyResolver(ipKeyResolver)))
                    .uri("lb://blog-service"))
        // User Service routes
        .route(
            r ->
                r.path("/api/v1/users/**")
                    .filters(
                        f ->
                            f.requestRateLimiter(
                                config ->
                                    config
                                        .setRateLimiter(rateLimiter)
                                        .setKeyResolver(ipKeyResolver)))
                    .uri("lb://user-service"))
        // Auth Service routes
        .route(
            r ->
                r.path("/api/v1/auth/**")
                    .filters(
                        f ->
                            f.requestRateLimiter(
                                config ->
                                    config
                                        .setRateLimiter(rateLimiter)
                                        .setKeyResolver(ipKeyResolver)))
                    .uri("lb://auth-service"))
        // Media Service routes
        .route(
            r ->
                r.path("/api/v1/media/**")
                    .filters(
                        f ->
                            f.requestRateLimiter(
                                config ->
                                    config
                                        .setRateLimiter(rateLimiter)
                                        .setKeyResolver(ipKeyResolver)))
                    .uri("lb://media-service"))
        // Payment Service routes
        .route(
            r ->
                r.path("/api/v1/payments/**")
                    .filters(
                        f ->
                            f.requestRateLimiter(
                                config ->
                                    config
                                        .setRateLimiter(rateLimiter)
                                        .setKeyResolver(ipKeyResolver)))
                    .uri("lb://payment-service"))
        // Interaction Service routes
        .route(
            r ->
                r.path("/api/v1/interactions/**")
                    .filters(
                        f ->
                            f.requestRateLimiter(
                                config ->
                                    config
                                        .setRateLimiter(rateLimiter)
                                        .setKeyResolver(ipKeyResolver)))
                    .uri("lb://interaction-service"))
        // Notification Service routes
        .route(
            r ->
                r.path("/api/v1/notifications/**")
                    .filters(
                        f ->
                            f.requestRateLimiter(
                                config ->
                                    config
                                        .setRateLimiter(rateLimiter)
                                        .setKeyResolver(ipKeyResolver)))
                    .uri("lb://notification-service"))
        // AI Service routes
        .route(
            r ->
                r.path("/api/v1/ai/**")
                    .filters(
                        f ->
                            f.requestRateLimiter(
                                config ->
                                    config
                                        .setRateLimiter(rateLimiter)
                                        .setKeyResolver(ipKeyResolver)))
                    .uri("lb://ai-service"))
        // Admin Service routes
        .route(
            r ->
                r.path("/api/v1/admin/**")
                    .filters(
                        f ->
                            f.requestRateLimiter(
                                config ->
                                    config
                                        .setRateLimiter(rateLimiter)
                                        .setKeyResolver(ipKeyResolver)))
                    .uri("lb://admin-service"))
        .build();
  }
}
