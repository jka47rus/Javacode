package com.example.Library.service;

import com.example.Library.entity.Author;
import com.example.Library.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorService {

    private AuthorRepository authorRepository;

    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }
}
