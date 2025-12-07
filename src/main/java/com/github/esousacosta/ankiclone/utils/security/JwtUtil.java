package com.github.esousacosta.ankiclone.utils.security;

import com.github.esousacosta.ankiclone.services.TokenBlacklistService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;

import java.nio.charset.StandardCharsets;

import java.util.Date;
import java.util.NoSuchElementException;

@Slf4j
public class JwtUtil {
  private final SecretKey key;
  private final long expirationMillis;
  private final TokenBlacklistService tokenBlacklistService;

  public JwtUtil(String secret, long expirationMillis, TokenBlacklistService tokenBlacklistService) {
    byte[] secretBytes = secret.getBytes(StandardCharsets.UTF_8);
    if (secret == null || secretBytes.length < 32) {
      throw new IllegalArgumentException("JWT secret must be at least 256 bits (32 bytes) long.");
    }
    this.key = Keys.hmacShaKeyFor(secretBytes);
    this.expirationMillis = expirationMillis;
    this.tokenBlacklistService = tokenBlacklistService;
  }

  public String generateToken(String username) {
    long now = System.currentTimeMillis();
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date(now))
        .setExpiration(new Date(now + expirationMillis))
        .signWith(key)
        .compact();
  }

  private Claims extractAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
  }

  public String extractUsernameFromToken(String token) {
    Claims claims = extractAllClaimsFromToken(token);
    return claims.getSubject();
  }

  public boolean isTokenExpired(String token) {
    Claims claims = extractAllClaimsFromToken(token);
    return claims.getExpiration().before(new Date());
  }

  public void invalidateToken(String token) {
    tokenBlacklistService.blacklistToken(token);
  }

  public boolean isTokenValid(String token, String username) {
    if (tokenBlacklistService.isTokenBlacklisted(token)) {
      log.info("JWT token is blacklisted.");
      return false;
    }
    try {
      String tokenUsername = extractUsernameFromToken(token);
      return tokenUsername != null && tokenUsername.equals(username) && !isTokenExpired(token);
    } catch (ExpiredJwtException e) {
      log.error("JWT token has expired: {}", e.getMessage());
      throw new IllegalArgumentException("Session has expired. Please log in again.");
    } catch (NoSuchElementException e) {
      throw new IllegalArgumentException("Invalid username or password.");
    }
    catch (Exception e) {
      log.debug("Invalid JWT token: {}", e.getMessage());
      return false;
    }
  }
}
