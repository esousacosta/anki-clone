package com.github.esousacosta.ankiclone.services;

import com.github.esousacosta.ankiclone.data.enums.ReviewQuality;
import com.github.esousacosta.ankiclone.data.models.card.Card;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SpacedRepetitionService {

  private static final int MIN_INTERVAL_DAYS = 1;
  private static final double MIN_EASE_FACTOR = 1.3;
  private static final int SECOND_CORRECT_ANSWER_INTERVAL = 4;
  private static final int MAX_QUALITY = 5;

  public void processCardReview(Card card, ReviewQuality quality) {
    if (card == null || quality == null) {
      throw new IllegalArgumentException("Card and quality must not be null");
    }

    // Update ease factor first (before calculating new interval)
    double newEaseFactor = card.getEaseFactor() + (0.1 - (MAX_QUALITY - quality.getValue()) * (0.08 + (MAX_QUALITY - quality.getValue()) * 0.02));
    card.setEaseFactor(Math.max(MIN_EASE_FACTOR, newEaseFactor));

    if (quality == ReviewQuality.WRONG) {
      // Reset on incorrect answer
      card.setIntervalDays(1);
      card.setCurrentConsecutiveCorrectAnswers(0);
      card.setWasLastAttemptCorrect(false);
      card.setNextReviewDate(LocalDateTime.now());
    } else {
      // Correct answer - calculate interval using ease factor
      int consecutiveCorrect = card.getCurrentConsecutiveCorrectAnswers();
      int newInterval;

      if (consecutiveCorrect == 0) {
        // First correct answer
        newInterval = MIN_INTERVAL_DAYS;
      } else if (consecutiveCorrect == 1) {
        // Second correct answer
        newInterval = SECOND_CORRECT_ANSWER_INTERVAL;
      } else {
        // Subsequent reviews: use ease factor to calculate interval
        // Apply quality-based modifier to the ease factor
        newInterval = (int) Math.round(card.getIntervalDays() * card.getEaseFactor() * quality.getModifier());
      }

      newInterval = Math.max(MIN_INTERVAL_DAYS, newInterval); // Ensure at least 1 day
      card.setIntervalDays(newInterval);
      card.setWasLastAttemptCorrect(true);
      card.setCurrentConsecutiveCorrectAnswers(consecutiveCorrect + 1);
      card.setNextReviewDate(LocalDateTime.now().plusDays(newInterval));
    }
  }
}

