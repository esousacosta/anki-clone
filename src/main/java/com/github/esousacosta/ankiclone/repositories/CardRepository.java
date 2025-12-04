package com.github.esousacosta.ankiclone.repositories;

import com.github.esousacosta.ankiclone.models.card.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Integer> {
  List<Card> findByUserId(int userId);
}
