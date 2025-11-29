package com.github.esousacosta.ankiclone.services;

import com.github.esousacosta.ankiclone.models.card.*;
import com.github.esousacosta.ankiclone.models.user.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(int id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
