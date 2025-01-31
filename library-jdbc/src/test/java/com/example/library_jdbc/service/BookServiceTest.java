package com.example.library_jdbc.service;

import com.example.library_jdbc.entity.Book;
import com.example.library_jdbc.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

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

        when(bookRepository.findAll()).thenReturn(List.of(book1, book2));

        List<Book> books = bookService.getAllBooks();

        assertEquals(2, books.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getBookById() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("1984");
        book.setAuthor("George Orwell");
        book.setPublicationYear(1949);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> foundBook = bookService.getBookById(1L);

        assertTrue(foundBook.isPresent());
        assertEquals("1984", foundBook.get().getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void saveBook() {
        Book book = new Book();
        book.setTitle("1984");
        book.setAuthor("George Orwell");
        book.setPublicationYear(1949);

        when(bookRepository.save(book)).thenReturn(book);

        Book savedBook = bookService.saveBook(book);

        assertNotNull(savedBook);
        assertEquals("1984", savedBook.getTitle());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void deleteBook() {
        bookService.deleteBook(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }
}