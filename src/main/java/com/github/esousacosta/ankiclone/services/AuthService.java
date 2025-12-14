package com.github.esousacosta.ankiclone.services;

import com.github.esousacosta.ankiclone.data.models.dtos.LoginRequestDto;
import com.github.esousacosta.ankiclone.data.models.dtos.LoginResponseDto;
import com.github.esousacosta.ankiclone.data.models.user.User;
import com.github.esousacosta.ankiclone.utils.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
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

  public LoginResponseDto createSession(LoginRequestDto loginRequest) throws IllegalArgumentException {
    try {
      User user = userService.getUserByUsername(loginRequest.usernameOrEmail());
      log.info("Attempting authentication for user: {}", user);

      if (user == null || !passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
        throw new IllegalArgumentException("Invalid username or password.");
      }

      // In a real application, you would return a JWT or session token here
      log.info("Password authentication successful for user: {}", user.getUsername());
      return new LoginResponseDto(jwtUtil.generateToken(user.getUsername()));
    } catch (Exception e) {
      log.info("Authentication failed for user: {}", loginRequest.usernameOrEmail());
      log.info("Error: {}", e.getMessage());
      throw new IllegalArgumentException("Invalid username or password.");
    }
  }

  public String logoutUser(HttpServletRequest request) {
    String token = extractTokenFromRequest(request);
    if (token != null) {
      jwtUtil.invalidateToken(token);
      log.info("User logged out successfully.");
      return "User logged out successfully.";
    }
    log.warn("Logout attempt with missing or invalid Authorization header");
    return "No valid token found in request.";
  }

  private String extractTokenFromRequest(HttpServletRequest request) {
    String header = request.getHeader("Authorization");
    if (header == null && !header.startsWith("Bearer ")) {
      return null;
    }
    return header.substring(7);
  }
}
