package com.example.oauth2.service;

import com.example.oauth2.Entity.Role;
import com.example.oauth2.Entity.User;
import com.example.oauth2.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;

@Service
@AllArgsConstructor
public class SocialAppService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {


    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(SocialAppService.class);


    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);


        Map<String, Object> attributes = oAuth2User.getAttributes();


        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        logger.info("Received attributes: {}", attributes);

        Role role = determineRole(registrationId, attributes);

        User user = createUserIfNotExists(attributes, role);
        logger.info("User logged in: {}, {}", user.getName(), user.getEmail());

        return new DefaultOAuth2User(
                Collections.singletonList(new SimpleGrantedAuthority(role.name())),
                attributes,
                "login"
        );
    }

    private Role determineRole(String registrationId, Map<String, Object> attributes) {
        if ("github".equals(registrationId)) {
            String login = (String) attributes.get("login");
            if (login != null && login.contains("admin")) {
                return Role.ADMIN;
            }
        }
        return Role.USER;
    }

    private User createUserIfNotExists(Map<String, Object> attributes, Role role) {
        String email = (String) attributes.get("email");
        String name = (String) attributes.getOrDefault("name", attributes.get("login")); // Используем 'login', если нет 'name'

        return userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User();

            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setRole(role);
            newUser.setAccountNonLocked(true);
            return userRepository.save(newUser);
        });
    }

}

