package com.github.esousacosta.ankiclone.data.enums;

public enum ReviewQuality {
  WRONG(0, 0.0),
  VERY_HARD(1, 0.6),
  HARD(2, 0.8),
  OK(3, 1.2),
  EASY(4, 1.4),
  VERY_EASY(5, 1.6);

  private final int value;
  private final double modifier;

  ReviewQuality(int value, double modifier) {
    this.value = value;
    this.modifier = modifier;
  }

  public int getValue() {
    return value;
  }

  public double getModifier() {
    return modifier;
  }
}
