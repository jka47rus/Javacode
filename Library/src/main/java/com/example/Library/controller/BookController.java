package com.example.Library.controller;

import com.example.Library.dto.BookFilter;
import com.example.Library.entity.Author;
import com.example.Library.entity.Book;
import com.example.Library.exception.ResourceNotFoundException;
import com.example.Library.service.AuthorService;
import com.example.Library.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookController {

    private BookService bookService;

    private AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(BookFilter filter) {

        return ResponseEntity.ok(bookService.findAll(filter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> bookOptional = bookService.findById(id);

        if (!bookOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(bookOptional.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@Validated @RequestBody Book book) {
        if (book.getAuthor() != null) {
            Author author = authorService.findById(book.getAuthor().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Author not found"));

            book.setAuthor(author);
        }

        Book savedBook = bookService.save(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Validated @RequestBody Book bookDetails) {
        return bookService.findById(id)
                .map(book -> {
                    book.setTitle(bookDetails.getTitle());
                    book.setYearPublished(bookDetails.getYearPublished());
                    book.setAuthor(bookDetails.getAuthor());
                    Book updetedBook = bookService.save(book);
                    return ResponseEntity.ok(updetedBook);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable Long id) {
        return bookService.findById(id)
                .map(user -> {
                    bookService.delete(user);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}