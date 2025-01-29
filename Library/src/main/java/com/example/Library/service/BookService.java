package com.example.Library.service;

import com.example.Library.dto.BookFilter;
import com.example.Library.entity.Book;
import com.example.Library.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {

    private BookRepository bookRepository;

    public List<Book> findAll(BookFilter bookFilter) {
        return bookRepository.findAll(PageRequest.of(bookFilter.getPageNumber(), bookFilter.getPageSize())).getContent();
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public void delete(Book book) {
        bookRepository.delete(book);
    }

    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }
}
