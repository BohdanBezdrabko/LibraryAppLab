package com.example.booklibrary.controllers;

import com.example.booklibrary.models.ReadingProgress;
import com.example.booklibrary.services.ReadingProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/reading-progress")
@RequiredArgsConstructor
public class ReadingProgressController {

    private final ReadingProgressService readingProgressService;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping
    public ResponseEntity<ReadingProgress> createReadingProgress(@RequestBody ReadingProgress progress) {
        return ResponseEntity.ok(readingProgressService.saveReadingProgress(progress));
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/{userId}")
    public ResponseEntity<List<ReadingProgress>> getUserReadingProgress(@PathVariable UUID userId) {
        return ResponseEntity.ok(readingProgressService.getReadingProgressByUserId(userId));
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<ReadingProgress> updateReadingProgress(@PathVariable UUID id, @RequestBody ReadingProgress progress) {
        return ResponseEntity.ok(readingProgressService.updateReadingProgress(id, progress));
    }
}
