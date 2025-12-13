package com.github.esousacosta.ankiclone.data.models.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

public record CardReviewDto(
        @Min(value = 0, message = "Quality must be between 0 and 3")
        @Max(value = 3, message = "Quality must be between 0 and 3")
        int quality) {
}

