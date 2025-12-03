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
    @Column(name = "last_attempt_correct", nullable = false)
    private boolean lastAttemptCorrect;
    @Column(name = "last_reviewed", nullable = false)
    private LocalDateTime lastReviewed;
}
