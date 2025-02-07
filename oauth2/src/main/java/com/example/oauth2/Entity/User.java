package com.example.oauth2.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "`user`")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String name;

    private boolean accountNonLocked = true;

    @Enumerated(EnumType.STRING)
    private Role role;

}