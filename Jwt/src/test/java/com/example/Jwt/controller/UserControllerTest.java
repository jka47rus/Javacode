package com.example.Jwt.controller;


import com.example.Jwt.entity.Role;
import com.example.Jwt.entity.User;
import com.example.Jwt.repository.UserRepository;
import com.example.Jwt.service.JWTUtils;
import com.example.Jwt.service.OurUserDetailedService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private MockMvc mockMvc;

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private JWTUtils jwtUtils;

    private OurUserDetailedService ourUserDetailedService;

    @Autowired
    public UserControllerTest(MockMvc mockMvc, UserRepository userRepository, PasswordEncoder passwordEncoder,
                              JWTUtils jwtUtils, OurUserDetailedService ourUserDetailedService) {
        this.mockMvc = mockMvc;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.ourUserDetailedService = ourUserDetailedService;
    }

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("password"));
        user.setRole(Role.USER);
        userRepository.save(user);

        User moderator = new User();
        moderator.setUsername("moderator");
        moderator.setPassword(passwordEncoder.encode("password"));
        moderator.setRole(Role.MODERATOR);
        userRepository.save(moderator);

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("password"));
        admin.setRole(Role.SUPER_ADMIN);
        userRepository.save(admin);
    }

    @Test
    void testUserAccess() throws Exception {
        UserDetails userDetails = ourUserDetailedService.loadUserByUsername("user");
        String token = jwtUtils.generateToken(userDetails);

        mockMvc.perform(get("/api/user")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testModeratorAccess() throws Exception {
        UserDetails userDetails = ourUserDetailedService.loadUserByUsername("moderator");
        String token = jwtUtils.generateToken(userDetails);

        mockMvc.perform(get("/api/moderator")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testAdminAccess() throws Exception {
        UserDetails userDetails = ourUserDetailedService.loadUserByUsername("admin");
        String token = jwtUtils.generateToken(userDetails);

        mockMvc.perform(get("/api/admin")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testUnauthorizedAccess() throws Exception {
        mockMvc.perform(get("/api/user"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testAccessDeniedForWrongRole() throws Exception {
        UserDetails userDetails = ourUserDetailedService.loadUserByUsername("user");
        String token = jwtUtils.generateToken(userDetails);

        mockMvc.perform(get("/api/admin")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }
}
