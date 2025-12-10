package com.github.esousacosta.ankiclone.services;

import com.github.esousacosta.ankiclone.models.deck.Deck;
import com.github.esousacosta.ankiclone.models.dtos.DeckDto;
import com.github.esousacosta.ankiclone.models.user.User;
import com.github.esousacosta.ankiclone.repositories.DeckRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeckService {
  private final DeckRepository deckRepository;
  private final UserService userService;

  public DeckService(DeckRepository deckRepository, UserService userService) {
    this.deckRepository = deckRepository;
    this.userService = userService;
  }

  public List<Deck> getDecksByOwnerId(int ownerId) {
    return deckRepository.findByOwnerId(ownerId);
  }


  public DeckDto createDeck(DeckDto deck) {
    User authenticatedUser = userService.getAuthenticatedUser();

    Deck newDeck = new Deck();
    newDeck.setName(deck.name());
    newDeck.setOwner(authenticatedUser);
    newDeck.setCreatedAt(LocalDateTime.now());
    deckRepository.save(newDeck);

    return deck;
  }
}
