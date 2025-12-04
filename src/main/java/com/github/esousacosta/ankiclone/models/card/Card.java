package com.github.esousacosta.ankiclone.models.card;

import com.github.esousacosta.ankiclone.models.deck.Deck;
import com.github.esousacosta.ankiclone.models.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cards")
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @ManyToOne(optional = false)
    @JoinColumn(name = "deck_id", referencedColumnName = "id")
    private Deck deck;
    @Column(name = "front", nullable = false)
    private String front;
    @Column(name = "back", nullable = false)
    private String back;
    @Column(name = "category", nullable = true)
    private String category;
    @Column(name = "ease_factor", nullable = false)
    private double easeFactor;
    @Column(name = "was_last_attempt_correct")
    private boolean wasLastAttemptCorrect;
    @Column(name = "last_reviewed", nullable = true)
    private LocalDateTime lastReviewed;
    @Column(name = "times_reviewed", nullable = false)
    private int timesReviewed;
    @Column(name = "next_review_date", nullable = false)
    private LocalDateTime nextReviewDate;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;
    @Column(name = "interval_days", nullable = false)
    private int intervalDays;
}
