package com.github.esousacosta.ankiclone.services;

import com.github.esousacosta.ankiclone.models.dtos.LoginRequestDto;
import com.github.esousacosta.ankiclone.models.user.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AuthService {
  private UserService userService;
  private PasswordEncoder passwordEncoder;

  public AuthService(UserService userService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  public String authenticate(LoginRequestDto loginRequest) throws IllegalArgumentException {
    try {
      User user = userService.getUserByUsername(loginRequest.getUsernameOrEmail());

      if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
        throw new IllegalArgumentException("Invalid username or password.");
      }

      // In a real application, you would return a JWT or session token here
      return "Authentication successful for user: " + user.getUsername();
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid username or password.");
    }

  }
}
