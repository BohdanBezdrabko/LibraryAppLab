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
    private final NotificationProducer notificationProducer;

    public List<ReadingProgress> getReadingProgressByUserId(UUID userId) {
        return readingProgressRepository.findByUserId(userId);
    }

    public ReadingProgress saveReadingProgress(ReadingProgress readingProgress) {
        ReadingProgress saved = readingProgressRepository.save(readingProgress);
        notificationProducer.send("Збережено прогрес читання для книги з ID: " + saved.getId());
        return saved;
    }

    public ReadingProgress updateReadingProgress(UUID id, ReadingProgress progressDetails) {
        return readingProgressRepository.findById(id)
                .map(progress -> {
                    progress.setCurrentPage(progressDetails.getCurrentPage());
                    progress.setPercentageRead(progressDetails.getPercentageRead());
                    progress.setUpdatedAt(progressDetails.getUpdatedAt());
                    ReadingProgress updated = readingProgressRepository.save(progress);
                    notificationProducer.send("Оновлено прогрес читання для книги з ID: " + updated.getId());
                    return updated;
                }).orElseThrow(() -> new RuntimeException("Progress not found"));
    }
}
