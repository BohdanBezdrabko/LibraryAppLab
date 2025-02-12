package com.example.booklibrary.services;

import com.example.booklibrary.models.Book;
import com.example.booklibrary.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(UUID id) {
        return bookRepository.findById(id);
    }

    public List<Book> filterBooks(String author, String genre, Integer year) {
        if (author != null) return bookRepository.findByAuthor(author);
        if (genre != null) return bookRepository.findByGenre(genre);
        if (year != null) return bookRepository.findByPublicationYear(year);
        return bookRepository.findAll();
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(UUID id, Book bookDetails) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(bookDetails.getTitle());
                    book.setAuthor(bookDetails.getAuthor());
                    book.setGenre(bookDetails.getGenre());
                    book.setDescription(bookDetails.getDescription());
                    book.setPublicationYear(bookDetails.getPublicationYear());
                    book.setFileUrl(bookDetails.getFileUrl());
                    return bookRepository.save(book);
                }).orElseThrow(() -> new RuntimeException("Book not found"));
    }
}
