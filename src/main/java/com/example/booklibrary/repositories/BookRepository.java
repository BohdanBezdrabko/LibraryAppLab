package com.example.booklibrary.repositories;

import com.example.booklibrary.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    List<Book> findByAuthor(String author);
    List<Book> findByGenre(String genre);
    List<Book> findByPublicationYear(int year);
}
