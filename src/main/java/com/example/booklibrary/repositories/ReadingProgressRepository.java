package com.example.booklibrary.repositories;

import com.example.booklibrary.models.ReadingProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReadingProgressRepository extends JpaRepository<ReadingProgress, UUID> {
    List<ReadingProgress> findByUserId(UUID userId);
}
