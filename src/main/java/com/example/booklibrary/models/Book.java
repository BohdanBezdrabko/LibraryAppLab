package com.example.booklibrary.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title;
    private String author;
    private String genre;

    @Column(columnDefinition = "TEXT")
    private String description;

    private int publicationYear;
    private String fileUrl;

    @Column(updatable = false)
    private String createdAt;
}
