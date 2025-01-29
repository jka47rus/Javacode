package com.example.jsonView.entity;

import com.example.jsonView.dto.user.UserDetails;
import com.example.jsonView.dto.user.UserSummary;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Entity(name = "`user`")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(UserSummary.class)
    private Long id;

    @JsonView(UserSummary.class)
    private String name;

    @JsonView(UserSummary.class)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonView(UserDetails.class)
    private List<Order> orders;

    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

}
