package com.github.esousacosta.ankiclone.services;

import com.github.esousacosta.ankiclone.models.dtos.UserDto;
import com.github.esousacosta.ankiclone.models.user.*;
import com.github.esousacosta.ankiclone.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(UserDto user) throws IllegalArgumentException{
        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setUsername(user.getUsername());

        return userRepository.save(newUser);
    }

    public User getUserById(int id) throws NoSuchElementException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NoSuchElementException("user with ID " + id + " not found.");
        }
        return user.get();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
