package com.github.esousacosta.ankiclone.repositories;

import com.github.esousacosta.ankiclone.data.models.deck.Deck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeckRepository extends JpaRepository<Deck, Integer> {
    List<Deck> findByOwnerId(int id);
}
