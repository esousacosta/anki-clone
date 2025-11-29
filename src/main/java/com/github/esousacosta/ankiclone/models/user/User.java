package com.github.esousacosta.ankiclone.models.user;

import java.time.LocalDateTime;

public record User(int id, String firstName, String lastName, String username, LocalDateTime lastLoginDate) {
}
