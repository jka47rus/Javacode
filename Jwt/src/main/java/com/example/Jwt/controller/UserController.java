package com.example.Jwt.controller;

import com.example.Jwt.entity.User;
import com.example.Jwt.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String userProfile() {
        return "User Profile";
    }

    @GetMapping("/moderator")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorDashboard() {
        return "Moderator Dashboard";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public String adminDashboard() {
        return "Admin Dashboard";
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<User> singUp(@RequestBody User user) {
        User newUser = user;
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        return ResponseEntity.ok(userRepository.save(newUser));
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<User> singIn(@RequestBody User user) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        return ResponseEntity.ok(userRepository.findByUsername(user.getUsername()).orElseThrow());
    }


}