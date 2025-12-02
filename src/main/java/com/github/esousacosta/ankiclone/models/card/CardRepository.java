package com.github.esousacosta.ankiclone.models.card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface CardRepository extends JpaRepository<Card, Integer> {
    public List<Card> findByUserId(int userId);
}
