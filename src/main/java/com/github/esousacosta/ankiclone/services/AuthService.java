package com.github.esousacosta.ankiclone.services;

import com.github.esousacosta.ankiclone.models.dtos.LoginRequestDto;
import com.github.esousacosta.ankiclone.models.user.User;
import com.github.esousacosta.ankiclone.utils.security.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {
  private UserService userService;
  private PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  public AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
  }

  public String createSession(LoginRequestDto loginRequest) throws IllegalArgumentException {
    try {
      User user = userService.getUserByUsername(loginRequest.getUsernameOrEmail());
      log.info("Attempting authentication for user: {}", user);

      if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
        throw new IllegalArgumentException("Invalid username or password.");
      }

      // In a real application, you would return a JWT or session token here
      log.info("Password authentication successful for user: {}", user.getUsername());
      return jwtUtil.generateToken(user.getUsername());
    }
    catch (Exception e) {
      log.info("Authentication failed for user: {}", loginRequest.getUsernameOrEmail());
      log.info("Error: {}", e.getMessage());
      throw new IllegalArgumentException("Invalid username or password.");
    }

  }
}
