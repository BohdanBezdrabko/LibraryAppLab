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
public class ReadingProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID userId;
    private UUID bookId;

    private int currentPage;
    private float percentageRead;

    @Column(updatable = true)
    private String updatedAt;
}

