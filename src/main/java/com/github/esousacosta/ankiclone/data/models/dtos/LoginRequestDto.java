package com.github.esousacosta.ankiclone.data.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

public record LoginRequestDto(
        @NotBlank(message = "Username or Email is required for login")
        String usernameOrEmail,
        @NotBlank(message = "Password is required for login")
        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        String password) {
}
