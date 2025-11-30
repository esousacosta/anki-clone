package com.github.esousacosta.ankiclone.models.card;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CardRepository {

    private final Map<Integer, Card> cardsById = new ConcurrentHashMap<>();

    public CardRepository() {
        cardsById.put(1, new Card(1, 1, "Who discovered Brazil?", "Pedro Alvares Cabral", "History", true, LocalDateTime.now()));
        cardsById.put(2, new Card(2, 2, "Who discovered Mexico?", "Hernan Cortez", "History", false, LocalDateTime.now()));
    }

    public List<Card> findAll() {
        return cardsById.values().stream().toList();
    }

    public Card findById(int id) {
        return cardsById.get(id);
    }
}
