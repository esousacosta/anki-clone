package com.github.esousacosta.ankiclone.utils.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class HelperTools {
  private HelperTools() {
    throw new IllegalStateException("Utility class");
  }

  public static String sanitizeInput(String input) {
    if (input == null) {
      return null;
    }
    // Remove leading and trailing whitespace
    String sanitized = input.trim();
    // Replace potentially dangerous characters
    sanitized = sanitized.replaceAll("[<>\"'%;)(&+]", "");
    return sanitized;
  }

  public static String getAuthenticatedUserUsername() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }
}
