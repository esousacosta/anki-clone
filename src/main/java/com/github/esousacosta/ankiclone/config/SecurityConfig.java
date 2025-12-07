package com.github.esousacosta.ankiclone.config;

import com.github.esousacosta.ankiclone.services.TokenBlacklistService;
import com.github.esousacosta.ankiclone.services.UserService;
import com.github.esousacosta.ankiclone.utils.security.JwtAuthenticationFilter;
import com.github.esousacosta.ankiclone.utils.security.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
  // Configure these using application.properties or environment variables in a real application
  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.expiration-ms}")
  private long jwtExpirationMillis; // 10 hours

  private final TokenBlacklistService tokenBlacklistService;


  public SecurityConfig(TokenBlacklistService tokenBlacklistService) {
    this.tokenBlacklistService = tokenBlacklistService;
  }

  @Bean
  public JwtUtil jwtUtil() {
    return new JwtUtil(jwtSecret, jwtExpirationMillis, tokenBlacklistService);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, UserService userService, JwtUtil jwtUtil) throws Exception {
    JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtil, userService);

    http
      .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.disable())
        .authorizeHttpRequests(auth -> auth
        .requestMatchers("/auth/**").permitAll()
        .anyRequest().authenticated()
        )
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}

