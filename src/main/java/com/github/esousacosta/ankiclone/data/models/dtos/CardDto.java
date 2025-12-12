package com.github.esousacosta.ankiclone.data.models.dtos;

import jakarta.validation.constraints.NotBlank;

public record CardDto(
        @NotBlank(message = "The card must have a non-empty front side")
        String front,
        @NotBlank(message = "The card must have a non-empty back side")
        String back,
        String category,
        Integer deckId) {
}
