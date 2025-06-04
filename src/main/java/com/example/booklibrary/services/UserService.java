package com.example.booklibrary.services;

import com.example.booklibrary.models.User;
import com.example.booklibrary.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final NotificationProducer notificationProducer;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        User saved = userRepository.save(user);
        notificationProducer.send("Збережено користувача: " + saved.getEmail());
        return saved;
    }

    public void deleteUser(UUID id) {
        Optional<User> user = userRepository.findById(id);
        userRepository.deleteById(id);
        user.ifPresent(u -> notificationProducer.send("Видалено користувача: " + u.getEmail()));
    }
}
