package com.example.library_jdbc.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("books")
public class Book {

    @Id
    private Long id;
    private String title;
    private String author;
    private int publicationYear;
}