package com.github.esousacosta.ankiclone.repositories;

import com.github.esousacosta.ankiclone.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
