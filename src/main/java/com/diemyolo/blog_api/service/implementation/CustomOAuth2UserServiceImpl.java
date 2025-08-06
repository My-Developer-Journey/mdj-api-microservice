package com.diemyolo.blog_api.service.implementation;

import com.diemyolo.blog_api.entity.Enumberable.Provider;
import com.diemyolo.blog_api.entity.Enumberable.Role;
import com.diemyolo.blog_api.entity.Enumberable.Status;
import com.diemyolo.blog_api.entity.OAuth2.CustomOAuth2User;
import com.diemyolo.blog_api.entity.User;
import com.diemyolo.blog_api.repository.UserRepository;
import com.diemyolo.blog_api.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class CustomOAuth2UserServiceImpl implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        try {
            // Lấy thông tin OAuth2User mặc định
            OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
            OAuth2User oAuth2User = delegate.loadUser(userRequest);

            // Lấy thông tin từ attributes
            Map<String, Object> attributes = oAuth2User.getAttributes();
            String email = (String) attributes.get("email");
            String name = (String) attributes.get("name");
            String picture = (String) attributes.get("picture");

            // Tìm người dùng trong database
            User user = userRepository.findByEmail(email).orElseGet(() -> {
                // Nếu không tồn tại, tạo người dùng mới
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setDisplayName(name);
                newUser.setAvatar(picture);
                newUser.setProvider(Provider.GOOGLE);
                newUser.setRole(Role.USER);
                newUser.setStatus(Status.ACTIVE);
                newUser.setEnabled(true);
                return userRepository.save(newUser);
            });

            // Lấy `nameAttributeKey` từ client registration
            String nameAttributeKey = userRequest.getClientRegistration()
                    .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

            // Tạo danh sách quyền hạn (authorities). Ví dụ: ROLE_USER
            List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

            // Trả về CustomOAuth2User đã được tạo
            return new CustomOAuth2User(user, authorities, attributes, nameAttributeKey);
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Cannot load info from OAuth2AccessToken!", e);
        }
    }
}