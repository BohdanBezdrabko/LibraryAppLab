package com.example.booklibrary.controllers;

import com.example.booklibrary.models.User;
import com.example.booklibrary.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(403).body("Access Denied");
        }

        Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
        if (user.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        return ResponseEntity.ok(user);
    }
}
