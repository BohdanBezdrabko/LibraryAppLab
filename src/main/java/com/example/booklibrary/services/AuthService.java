package com.example.booklibrary.services;

import com.example.booklibrary.dtos.AuthRequest;
import com.example.booklibrary.dtos.AuthResponse;
import com.example.booklibrary.models.Role;
import com.example.booklibrary.models.User;
import com.example.booklibrary.repositories.UserRepository;
import com.example.booklibrary.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(user.getRole() != null ? user.getRole() : Role.USER); // За замовчуванням USER
        userRepository.save(user);

        // Використовуємо .name() для конвертації ENUM у String
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponse(token);
    }

    public AuthResponse authenticate(AuthRequest request) {
        Optional<User> user = userRepository.findByEmail(request.getEmail());

        if (user.isPresent() && passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
            // Використовуємо .name() для передачі ролі у вигляді рядка
            String token = jwtUtil.generateToken(user.get().getEmail(), user.get().getRole().name());
            return new AuthResponse(token);
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}
