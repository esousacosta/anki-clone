package com.github.esousacosta.ankiclone.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {
  @NotBlank(message = "Username or Email is required for login")
  private String usernameOrEmail;

  @NotBlank(message = "Password is required for login")
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;
}
