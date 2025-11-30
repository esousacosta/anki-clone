package com.github.esousacosta.ankiclone.controller;

import com.github.esousacosta.ankiclone.models.dtos.UserDto;
import com.github.esousacosta.ankiclone.models.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import com.github.esousacosta.ankiclone.services.UserService;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/{id}")
    public User getUserById(@PathVariable int id) {
            return userService.getUserById(id);
    }

    @PostMapping
    public User createUser(@RequestBody UserDto user) {
        return userService.createUser(user);
    }

}
