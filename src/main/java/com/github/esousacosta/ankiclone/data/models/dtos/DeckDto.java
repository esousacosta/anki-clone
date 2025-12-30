package com.github.esousacosta.ankiclone.data.models.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;


public record DeckDto(
        @NotBlank(message = "The deck must have a non-empty name")
        String name,
        String description,
        Integer numberOfCardsToReview,
        Integer id) {
}
