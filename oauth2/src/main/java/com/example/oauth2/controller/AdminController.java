package com.example.oauth2.controller;

import com.example.oauth2.Entity.User;
import com.example.oauth2.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class AdminController {

    private final UserRepository userRepository;

    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard() {
        return "Admin Dashboard";
    }

    @GetMapping("/users")
    public List<User> hetAllUsers() {
        return userRepository.findAll();
    }
}