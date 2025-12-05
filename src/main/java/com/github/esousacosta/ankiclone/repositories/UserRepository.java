package com.github.esousacosta.ankiclone.repositories;

import com.github.esousacosta.ankiclone.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByUsername(String username);
}
