package com.github.esousacosta.ankiclone.models.card;

import java.time.LocalDateTime;

public record Card(int id, String front, String back, String category, boolean lastStatus, LocalDateTime lastReviewed) {
}
