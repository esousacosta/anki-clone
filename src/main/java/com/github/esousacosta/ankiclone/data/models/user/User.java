package com.github.esousacosta.ankiclone.data.models.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;
    @Column(name = "last_login_date", nullable = true)
    private LocalDateTime lastLoginDate;
}
