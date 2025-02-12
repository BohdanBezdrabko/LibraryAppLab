package com.example.booklibrary.services;

import com.example.booklibrary.models.ReadingProgress;
import com.example.booklibrary.repositories.ReadingProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadingProgressService {

    private final ReadingProgressRepository readingProgressRepository;

    public List<ReadingProgress> getReadingProgressByUserId(UUID userId) {
        return readingProgressRepository.findByUserId(userId);
    }

    public ReadingProgress saveReadingProgress(ReadingProgress readingProgress) {
        return readingProgressRepository.save(readingProgress);
    }

    public ReadingProgress updateReadingProgress(UUID id, ReadingProgress progressDetails) {
        return readingProgressRepository.findById(id)
                .map(progress -> {
                    progress.setCurrentPage(progressDetails.getCurrentPage());
                    progress.setPercentageRead(progressDetails.getPercentageRead());
                    progress.setUpdatedAt(progressDetails.getUpdatedAt());
                    return readingProgressRepository.save(progress);
                }).orElseThrow(() -> new RuntimeException("Progress not found"));
    }
}
