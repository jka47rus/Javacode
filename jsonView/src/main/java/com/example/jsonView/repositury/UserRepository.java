package com.example.jsonView.repositury;

import com.example.jsonView.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


}

