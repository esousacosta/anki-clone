package com.github.esousacosta.ankiclone.models.card;

import java.time.LocalDateTime;

public record Card(int id, int userId, String front, String back, String category, boolean lastAttemptCorrect, LocalDateTime lastReviewed) {
}
