package com.github.esousacosta.ankiclone.data.enums;

/**
 * Review quality ratings matching Anki's 4-button system.
 *
 * Anki's algorithm:
 * - AGAIN: Reset card, ease -= 0.2 (for review cards), interval *= 0.0
 * - HARD: ease -= 0.15, interval multiplier ~0.5-0.85 (shorter than GOOD)
 * - GOOD: ease unchanged, interval *= ease factor
 * - EASY: ease += 0.15, interval *= ease factor * easy_bonus (~1.3)
 */
public enum ReviewQuality {
  AGAIN(0, 0.0, -0.2),      // Red button - forgot the card
  HARD(1, 0.85, -0.15),     // Orange button - remembered with difficulty (shorter interval)
  GOOD(2, 1.0, 0.0),        // Green button - remembered correctly (neutral)
  EASY(3, 1.3, 0.15);       // Blue button - remembered very easily (longer interval)

  private final int value;
  private final double intervalModifier;
  private final double easeFactorChange;

  public static ReviewQuality fromValue(int value) {
    for (ReviewQuality quality : ReviewQuality.values()) {
      if (quality.getValue() == value) {
        return quality;
      }
    }

    throw new IllegalArgumentException("Invalid review quality value: " + value + ". Must be 0-3.");
  }

  ReviewQuality(int value, double intervalModifier, double easeFactorChange) {
    this.value = value;
    this.intervalModifier = intervalModifier;
    this.easeFactorChange = easeFactorChange;
  }

  public int getValue() {
    return value;
  }

  public double getIntervalModifier() {
    return intervalModifier;
  }

  public double getEaseFactorChange() {
    return easeFactorChange;
  }
}
