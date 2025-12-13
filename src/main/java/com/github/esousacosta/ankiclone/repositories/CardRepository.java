package com.github.esousacosta.ankiclone.repositories;

import com.github.esousacosta.ankiclone.data.models.card.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CardRepository extends JpaRepository<Card, Integer> {
  List<Card> findByUserId(int userId);

  /**
   * Find all cards that are due for review (nextReviewDate <= now).
   * Spring Data JPA automatically generates the query based on the method name.
   */
  List<Card> findByNextReviewDateLessThanEqual(LocalDateTime date);

  /**
   * Find all cards due for review for a specific user.
   * Combines user filtering with date filtering.
   */
  List<Card> findByUserIdAndNextReviewDateLessThanEqual(int userId, LocalDateTime date);

  /**
   * Find all cards due for review in a specific deck.
   */
  List<Card> findByDeckIdAndNextReviewDateLessThanEqual(int deckId, LocalDateTime date);
}
