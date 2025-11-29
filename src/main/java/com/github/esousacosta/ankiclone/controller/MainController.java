package com.github.esousacosta.ankiclone.controller;

import com.github.esousacosta.ankiclone.models.user.User;
import com.github.esousacosta.ankiclone.services.CardService;
import com.github.esousacosta.ankiclone.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {
    private final UserService userService;
    private final CardService cardService;

    public MainController(UserService userService, CardService cardService) {
        this.userService = userService;
        this.cardService = cardService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        // This is a placeholder implementation.
        // You would typically call a method from UserRepository to fetch all users.
        return userService.getAllUsers();
    }
}
