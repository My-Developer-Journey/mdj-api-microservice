package com.diemyolo.blog_service.configuration;

import com.diemyolo.blog_service.entity.OAuth2.CustomOAuth2User;
import com.diemyolo.blog_service.entity.User;
import com.diemyolo.blog_service.service.JWTService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Configuration
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Value("${app.frontend.origin}")
    private String appFrontendOrigin;

    private final JWTService jwtService;

    public OAuth2LoginSuccessHandler(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // Lấy CustomOAuth2User từ Authentication
        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();

        // Lấy đối tượng User đã được lưu trong CustomOAuth2User
        User user = oauthUser.getUser();

        // Tạo JWT access token
        String accessToken = jwtService.generateToken(user);

        // Set token vào Cookie
        Cookie cookie = new Cookie("accessToken", accessToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge((int) (jwtExpiration / 1000));
        response.addCookie(cookie);

        // Chuyển hướng về FE
        response.sendRedirect(appFrontendOrigin);
    }
}