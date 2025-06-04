package com.example.booklibrary.services;

import com.example.booklibrary.models.User;
import com.example.booklibrary.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final NotificationProducer notificationProducer; // 📨 додано

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            notificationProducer.send(" Спроба входу: користувача не знайдено — " + email); //  помилка
            throw new UsernameNotFoundException("User not found");
        }

        notificationProducer.send("Завантажено користувача для аутентифікації: " + email); //  успіх

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.get().getEmail())
                .password(user.get().getPassword())
                .authorities("ROLE_" + user.get().getRole().name())
                .build();
    }
}
