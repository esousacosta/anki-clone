package com.github.esousacosta.ankiclone.services;

import com.github.esousacosta.ankiclone.models.dtos.UserDto;
import com.github.esousacosta.ankiclone.models.user.*;
import com.github.esousacosta.ankiclone.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.time.LocalDateTime;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public User saveUser(UserDto user) throws IllegalArgumentException{
      // This is probably not needed since we have validation in the controller, but just in case
      if (user.getPassword() == null || user.getPassword().length() < 8) {
          throw new IllegalArgumentException("password must be at least 8 characters long.");
      }

      User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        // set creation timestamp using LocalDateTime
        newUser.setCreatedAt(LocalDateTime.now());
        // hash the password before saving
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(newUser);
    }

    public User getUserById(int id) throws NoSuchElementException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NoSuchElementException("user with ID " + id + " not found.");
        }
        return user.get();
    }

    public User getUserByUsername(String username) throws NoSuchElementException {
      Optional<User> user = userRepository.findByUsername(username);
      if (user.isEmpty()) {
          throw new NoSuchElementException("user with username " + username + " not found.");
      }

      return user.get();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
