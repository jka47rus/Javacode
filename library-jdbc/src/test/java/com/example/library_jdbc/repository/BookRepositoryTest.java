package com.example.library_jdbc.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import com.example.library_jdbc.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@Import(BookRepository.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // Очистка контекста перед каждым тестом
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    public void testFindAll() {
        List<Book> books = bookRepository.findAll();
        assertThat(books).isEmpty();
    }

    @Test
    public void testSaveAndFindById() {
        Book book = new Book();
        book.setTitle("Spring in Action");
        book.setAuthor("Craig Walls");
        book.setPublicationYear(2018);

        Book savedBook = bookRepository.save(book);
        Optional<Book> foundBook = bookRepository.findById(savedBook.getId());

        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getTitle()).isEqualTo("Spring in Action");
        assertThat(foundBook.get().getAuthor()).isEqualTo("Craig Walls");
        assertThat(foundBook.get().getPublicationYear()).isEqualTo(2018);
    }

    @Test
    public void testDeleteById() {
        Book book = new Book();
        book.setTitle("Spring in Action");
        book.setAuthor("Craig Walls");
        book.setPublicationYear(2018);

        Book savedBook = bookRepository.save(book);
        bookRepository.deleteById(savedBook.getId());
        Optional<Book> foundBook = bookRepository.findById(savedBook.getId());

        assertThat(foundBook).isEmpty();
    }

    @Test
    public void testFindAllAfterSave() {
        Book book = new Book();
        book.setTitle("Spring in Action");
        book.setAuthor("Craig Walls");
        book.setPublicationYear(2018);

        bookRepository.save(book);

        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(1);
    }

    @Test
    public void testUpdateBook() {
        Book book = new Book();
        book.setTitle("Spring in Action");
        book.setAuthor("Craig Walls");
        book.setPublicationYear(2018);

        Book savedBook = bookRepository.save(book);

        savedBook.setTitle("Spring in Action 6th Edition");
        savedBook.setPublicationYear(2022);
        bookRepository.save(savedBook);

        Optional<Book> updatedBook = bookRepository.findById(savedBook.getId());
        assertThat(updatedBook).isPresent();
        assertThat(updatedBook.get().getTitle()).isEqualTo("Spring in Action 6th Edition");
        assertThat(updatedBook.get().getPublicationYear()).isEqualTo(2022);
    }
}