package com.github.esousacosta.ankiclone.services;

import com.github.esousacosta.ankiclone.models.dtos.UserDto;
import com.github.esousacosta.ankiclone.models.user.*;
import com.github.esousacosta.ankiclone.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserDto user) {
        return userRepository.createUser(user.firstName(), user.lastName(), user.userName());
    }

    public User getUserById(int id) throws NoSuchElementException {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new NoSuchElementException("user with ID " + id + " not found.");
        }
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
