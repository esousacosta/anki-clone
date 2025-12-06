package com.github.esousacosta.ankiclone.utils.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.SecretKey;

import java.nio.charset.StandardCharsets;

import java.util.Date;

public class JwtUtil {
  private final SecretKey key;
  private final long expirationMillis;

  public JwtUtil(String secret, long expirationMillis) {
    this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    this.expirationMillis = expirationMillis;
  }

  public String generateToken(String username) {
    long now = System.currentTimeMillis();
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date(now))
        .setExpiration(new Date(now + expirationMillis))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  public Claims extractAllClaimsFromToken(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
  }

  public String extractUsernameFromToken(String token) {
    Claims claims = extractAllClaimsFromToken(token);
    return claims.getSubject();
  }

  public boolean isTokenExpired(String token) {
    Claims claims = extractAllClaimsFromToken(token);
    return claims.getExpiration().before(new Date());
  }

  public boolean isTokenValid(String token, String username) {
    try {
      String tokenUsername = extractUsernameFromToken(token);
      return tokenUsername != null && tokenUsername.equals(username) && !isTokenExpired(token);
    } catch (Exception e) {
      return false;
    }
  }
}
