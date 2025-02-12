package com.example.booklibrary.controllers;

import com.example.booklibrary.dtos.AuthRequest;
import com.example.booklibrary.dtos.AuthResponse;
import com.example.booklibrary.models.User;
import com.example.booklibrary.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.authenticate(request);
    }
}
