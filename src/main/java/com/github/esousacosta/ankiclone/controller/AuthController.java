package com.github.esousacosta.ankiclone.controller;

import com.github.esousacosta.ankiclone.models.dtos.LoginRequestDto;
import com.github.esousacosta.ankiclone.models.dtos.UserDto;
import com.github.esousacosta.ankiclone.services.UserService;
import com.github.esousacosta.ankiclone.models.user.User;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
  private final UserService userService;

  public AuthController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping(path = "/register")
  public ResponseEntity<UserDto> registerUser(@RequestBody @Valid UserDto user) {
    log.info("Auth - Register endpoint called");
    User created = userService.saveUser(user);
    // Exclude password from response
    UserDto response = new UserDto(user.getFirstName(), user.getLastName(), user.getUsername(), null, user.getEmail());
    return ResponseEntity.created(null).body(response);
  }

  @PostMapping(path = "/login")
  public String loginUser(@RequestBody @Valid LoginRequestDto loginRequest) {
    log.info("Auth - Login endpoint called for user: {}", loginRequest.getUsernameOrEmail());
    // Authentication logic would go here
    return "Login successful for user: " + loginRequest.getUsernameOrEmail();
  }

}
