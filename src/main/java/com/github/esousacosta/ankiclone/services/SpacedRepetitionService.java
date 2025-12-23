package com.github.esousacosta.ankiclone.services;

import com.github.esousacosta.ankiclone.data.enums.ReviewQuality;
import com.github.esousacosta.ankiclone.data.models.card.Card;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SpacedRepetitionService {

  private static final int MIN_INTERVAL_DAYS = 1;
  private static final double MIN_EASE_FACTOR = 1.3;  // 130% in Anki
  private static final double DEFAULT_EASE_FACTOR = 2.5;  // 250% starting ease
  private static final int FIRST_INTERVAL_DAYS = 1;
  private static final int SECOND_INTERVAL_DAYS = 6;
  private static final double EASY_BONUS = 1.3;  // Anki's default easy bonus

  public void processCardReview(Card card, int qualityValue) throws IllegalArgumentException {
    ReviewQuality quality = ReviewQuality.fromValue(qualityValue);
    processCardReview(card, quality);
  }

  public void processCardReview(Card card, ReviewQuality quality) throws IllegalArgumentException {
    if (card == null || quality == null) {
      throw new IllegalArgumentException("Card and quality must not be null");
    }

    // Update review tracking
    card.setLastReviewed(LocalDateTime.now());
    card.setTimesReviewed(card.getTimesReviewed() + 1);

    if (quality == ReviewQuality.AGAIN) {
      // AGAIN: Reset the card (failed review)
      // Anki behavior: interval resets, ease factor decreases by 0.2 for review cards
      card.setIntervalDays(MIN_INTERVAL_DAYS);
      card.setCurrentStreak(0);
      card.setWasLastAttemptCorrect(false);
      card.setNextReviewDate(LocalDateTime.now());

      // Only reduce ease factor if card was already in review phase (streak > 0 before)
      // This matches Anki's behavior of not penalizing new cards
      if (card.getTimesReviewed() > 1) {
        double newEaseFactor = card.getEaseFactor() + quality.getEaseFactorChange();
        card.setEaseFactor(Math.max(MIN_EASE_FACTOR, newEaseFactor));
      }
    } else {
      // HARD, GOOD, or EASY: Correct answer
      // Update ease factor based on difficulty
      double newEaseFactor = card.getEaseFactor() + quality.getEaseFactorChange();
      card.setEaseFactor(Math.max(MIN_EASE_FACTOR, newEaseFactor));

      int consecutiveCorrect = card.getCurrentStreak();
      int newInterval = calculateInterval(card, quality, consecutiveCorrect);

      card.setIntervalDays(newInterval);
      card.setWasLastAttemptCorrect(true);
      card.setCurrentStreak(consecutiveCorrect + 1);
      card.setNextReviewDate(LocalDateTime.now().plusDays(newInterval));
    }
  }

  /**
   * Calculate the new interval following Anki's algorithm.
   *
   * New cards (streak 0-1): Fixed intervals (1 day, then 6 days)
   * Review cards (streak >= 2): interval = previous_interval * ease_factor * quality_modifier
   *
   * Quality modifiers:
   * - HARD: 0.85 (slower growth - you struggled, but interval still increases)
   * - GOOD: 1.0 (uses pure ease factor - standard growth)
   * - EASY: 1.3 (easy bonus - faster growth)
   *
   * Note: Even with HARD, intervals typically still increase (just slower),
   * as long as ease_factor > ~1.18 (since 1.18 * 0.85 ≈ 1.0)
   */
  private static int calculateInterval(Card card, ReviewQuality quality, int consecutiveCorrect) {
    int newInterval;

    if (consecutiveCorrect == 0) {
      // First correct answer: 1 day
      newInterval = FIRST_INTERVAL_DAYS;
    } else if (consecutiveCorrect == 1) {
      // Second correct answer: graduate to 6 days (Anki's default)
      newInterval = SECOND_INTERVAL_DAYS;
    } else {
      // Subsequent reviews: Apply Anki's formula
      // interval = previous_interval * ease_factor * quality_modifier
      //
      // For HARD (quality.intervalModifier = 0.85): interval *= ease_factor * 0.85 (shorter - struggled)
      // For GOOD (quality.intervalModifier = 1.0): interval *= ease_factor (standard)
      // For EASY (quality.intervalModifier = 1.3): interval *= ease_factor * 1.3 (longer - easy bonus)
      newInterval = (int) Math.round(
          card.getIntervalDays() * card.getEaseFactor() * quality.getIntervalModifier()
      );
    }

    // Ensure minimum interval of 1 day
    return Math.max(MIN_INTERVAL_DAYS, newInterval);
  }
}

