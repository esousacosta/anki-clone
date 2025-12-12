package com.github.esousacosta.ankiclone.data.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

public record UserDto(
        @NotBlank(message = "The user must have a non-empty first name")
        String firstName,
        String lastName,
        @NotBlank(message = "The user must have a non-empty username")
        String username,
        @NotBlank(message = "The user must have a non-empty password")
        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        String password,
        String email) {
}
