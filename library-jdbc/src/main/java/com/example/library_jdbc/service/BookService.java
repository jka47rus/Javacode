package com.example.library_jdbc.service;


import com.example.library_jdbc.entity.Book;
import com.example.library_jdbc.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;


    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }


    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }


    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }


    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}