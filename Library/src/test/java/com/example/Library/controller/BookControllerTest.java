package com.example.Library.controller;

import com.example.Library.entity.Author;
import com.example.Library.entity.Book;
import com.example.Library.exception.ResourceNotFoundException;
import com.example.Library.service.AuthorService;
import com.example.Library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookControllerTest {

    @Mock
    private BookService bookService;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testGetAllBooks_WithPageSizeAndPageNumber() {
        Book book1 = new Book();
        book1.setTitle("Book One");
        book1.setYearPublished(2021);

        Book book2 = new Book();
        book2.setTitle("Book Two");
        book2.setYearPublished(2022);

        List<Book> books = Arrays.asList(book1, book2);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());

        when(bookService.findAll(pageable)).thenReturn(bookPage);

        PagedModel<Book> result = bookController.getAllBook(pageable);

        assertEquals(2, result.getContent().size());
        assertEquals("Book One", result.getContent().get(0).getTitle());
        assertEquals("Book Two", result.getContent().get(1).getTitle());
    }

    @Test
    public void testGetBookById_Success() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Sample Book");

        when(bookService.findById(1L)).thenReturn(Optional.of(book));

        ResponseEntity<Book> response = bookController.getBookById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Sample Book", response.getBody().getTitle());

        verify(bookService, times(1)).findById(1L);
    }

    @Test
    public void testGetBookById_NotFound() {
        when(bookService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Book> response = bookController.getBookById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(bookService, times(1)).findById(1L);
    }

    @Test
    public void testCreateBook_Success() {
        Book book = new Book();
        book.setTitle("New Book");
        book.setYearPublished(2023);

        Author author = new Author();
        author.setId(1L);
        author.setName("Author Name");

        book.setAuthor(author);

        when(authorService.findById(1L)).thenReturn(Optional.of(author));
        when(bookService.save(any(Book.class))).thenReturn(book);

        ResponseEntity<Book> response = bookController.createBook(book);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("New Book", response.getBody().getTitle());

        verify(bookService, times(1)).save(any(Book.class));
    }

    @Test
    public void testUpdateBook_Success() {
        Book existingBook = new Book();
        existingBook.setId(1L);
        existingBook.setTitle("Existing Book");

        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Title");

        when(bookService.findById(1L)).thenReturn(Optional.of(existingBook));
        when(bookService.save(any(Book.class))).thenReturn(updatedBook);

        ResponseEntity<Book> response = bookController.updateBook(1L, updatedBook);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Title", response.getBody().getTitle());

        verify(bookService, times(1)).save(any(Book.class));
    }

    @Test
    public void testUpdateBook_NotFound() {
        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Title");

        when(bookService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Book> response = bookController.updateBook(1L, updatedBook);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(bookService, never()).save(any(Book.class));
    }

    @Test
    public void testDeleteBook_Success() {
        doNothing().when(bookService).delete(any(Book.class));
        Book book = new Book();
        book.setTitle("New Book");
        book.setYearPublished(2023);


        when(bookService.findById(1L)).thenReturn(Optional.of(book));
        ResponseEntity<Object> response = bookController.deleteBook(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(bookService, times(1)).delete(book);
    }

    @Test
    public void testDeleteBook_NotFound() {
        doThrow(new ResourceNotFoundException("Book not found")).when(bookService).delete(any(Book.class));

        ResponseEntity<Object> response = bookController.deleteBook(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(bookService, times(1)).findById(1L);
        verify(bookService, never()).delete(any(Book.class));
    }
}
