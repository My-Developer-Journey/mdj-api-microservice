package com.diemyolo.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(ApiGatewayApplication.class, args);
  }

  // Mock API Gateway - Implementing later
  @Bean
  public RouteLocator customRouteLocator(
      RouteLocatorBuilder builder, ThrottleGatewayFilterFactory throttle) {
    String mockUri = "http://httpbin.org:80";
    return builder
        .routes()
        .route(
            r ->
                r.host("**.abc.org")
                    .and()
                    .path("/image/png")
                    .filters(f -> f.addResponseHeader("X-TestHeader", "foobar"))
                    .uri(mockUri))
        .route(
            r ->
                r.path("/image/webp")
                    .filters(f -> f.addResponseHeader("X-AnotherHeader", "baz"))
                    .uri(mockUri)
                    .metadata("key", "value"))
        .route(
            r ->
                r.order(-1)
                    .host("**.throttle.org")
                    .and()
                    .path("/get")
                    .filters(f -> f.filter(throttle.apply(1, 1, 10, TimeUnit.SECONDS)))
                    .uri(mockUri)
                    .metadata("key", "value"))
        .build();
  }
}
