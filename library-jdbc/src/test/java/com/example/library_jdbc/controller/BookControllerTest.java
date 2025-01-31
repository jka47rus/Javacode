package com.example.library_jdbc.controller;

import com.example.library_jdbc.entity.Book;
import com.example.library_jdbc.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBooks() {
        Book book1 = new Book();
        book1.setTitle("1984");
        book1.setAuthor("George Orwell");
        book1.setPublicationYear(1949);

        Book book2 = new Book();
        book2.setTitle("To Kill a Mockingbird");
        book2.setAuthor("Harper Lee");
        book2.setPublicationYear(1960);

        when(bookService.getAllBooks()).thenReturn(List.of(book1, book2));

        List<Book> books = bookController.getAllBooks();

        assertEquals(2, books.size());
        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void getBookById_Found() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("1984");
        book.setAuthor("George Orwell");
        book.setPublicationYear(1949);

        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));

        ResponseEntity<Book> response = bookController.getBookById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("1984", response.getBody().getTitle());
        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void getBookById_NotFound() {
        when(bookService.getBookById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Book> response = bookController.getBookById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void createBook() {
        // Подготовка данных
        Book book = new Book();
        book.setTitle("1984");
        book.setAuthor("George Orwell");
        book.setPublicationYear(1949);

        when(bookService.saveBook(book)).thenReturn(book);

        ResponseEntity<Book> response = bookController.createBook(book);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("1984", response.getBody().getTitle());
        verify(bookService, times(1)).saveBook(book);
    }

    @Test
    void updateBook() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("1984");
        book.setAuthor("George Orwell");
        book.setPublicationYear(1949);

        when(bookService.saveBook(book)).thenReturn(book);

        ResponseEntity<Book> response = bookController.updateBook(1L, book);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("1984", response.getBody().getTitle());
        verify(bookService, times(1)).saveBook(book);
    }

    @Test
    void deleteBook() {
        ResponseEntity<Void> response = bookController.deleteBook(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(bookService, times(1)).deleteBook(1L);
    }
}