package com.github.esousacosta.ankiclone.models.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserDto(@NotBlank(message = "The user must have a non-empty first name") String firstName,
                      @NotBlank(message = "The user must have a non-empty last name") String lastName,
                      @NotBlank(message = "The user must have a non-empty username") String userName,
                      String password,
                      String email) {
}
