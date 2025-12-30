package com.github.esousacosta.ankiclone.controller;

import com.github.esousacosta.ankiclone.data.models.dtos.LoginRequestDto;
import com.github.esousacosta.ankiclone.data.models.dtos.LoginResponseDto;
import com.github.esousacosta.ankiclone.data.models.dtos.UserDto;
import com.github.esousacosta.ankiclone.services.AuthService;
import com.github.esousacosta.ankiclone.services.UserService;
import com.github.esousacosta.ankiclone.data.models.user.User;
import jakarta.servlet.http.HttpServletRequest;
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
  private final AuthService authService;

  public AuthController(UserService userService, AuthService authService) {
    this.userService = userService;
    this.authService = authService;
  }

  @PostMapping(path = "/register")
  public ResponseEntity<UserDto> registerUser(@RequestBody @Valid UserDto user) {
    log.info("Auth - Register endpoint called");
    userService.saveUser(user);
    // Exclude password from response
    UserDto response = new UserDto(user.firstName(), user.lastName(), user.username(), null, user.email());
    return ResponseEntity.created(null).body(response);
  }

  @PostMapping(path = "/login")
  public ResponseEntity<LoginResponseDto> loginUser(@RequestBody @Valid LoginRequestDto loginRequest) {
    log.info("Auth - Login endpoint called for user: {}", loginRequest.usernameOrEmail());
    return ResponseEntity.ok().body(authService.createSession(loginRequest));
  }

  @PostMapping(path = "/logout")
  public ResponseEntity<String> logoutUser(HttpServletRequest request) {
    log.info("Auth - Logout endpoint called");
    authService.logoutUser(request);
    return ResponseEntity.ok("User logged out successfully.");
  }

}
