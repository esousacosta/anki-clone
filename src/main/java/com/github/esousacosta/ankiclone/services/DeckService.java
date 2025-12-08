package com.github.esousacosta.ankiclone.services;

import com.github.esousacosta.ankiclone.models.deck.Deck;
import com.github.esousacosta.ankiclone.repositories.DeckRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeckService {
  private final DeckRepository deckRepository;

  public DeckService(DeckRepository deckRepository) {
    this.deckRepository = deckRepository;
  }

  public List<Deck> getDecksByOwnerId(int ownerId) {
    return deckRepository.findByOwnerId(ownerId);
  }
}
