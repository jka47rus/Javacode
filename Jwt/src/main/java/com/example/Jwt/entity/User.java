package com.example.Jwt.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "`User`")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;


    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean isAccountNonLocked = true;

}