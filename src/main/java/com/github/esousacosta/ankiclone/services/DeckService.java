package com.github.esousacosta.ankiclone.services;

import com.github.esousacosta.ankiclone.data.models.deck.Deck;
import com.github.esousacosta.ankiclone.data.models.dtos.DeckDto;
import com.github.esousacosta.ankiclone.data.models.user.User;
import com.github.esousacosta.ankiclone.repositories.DeckRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DeckService {
  private final DeckRepository deckRepository;
  private final UserService userService;

  public DeckService(DeckRepository deckRepository, UserService userService) {
    this.deckRepository = deckRepository;
    this.userService = userService;
  }

  public Deck getDeckById(int id) throws NoSuchElementException {
    return deckRepository.findById(id).
        orElseThrow(() -> new NoSuchElementException("Deck with ID " + id + " not found."));
  }

  public List<Deck> getDecksByOwnerId(int ownerId) {
    return deckRepository.findByOwnerId(ownerId);
  }


  public Deck createDeck(DeckDto deck) {
    User authenticatedUser = userService.getAuthenticatedUser();

    Deck newDeck = new Deck();
    newDeck.setName(deck.name());
    newDeck.setOwner(authenticatedUser);
    newDeck.setCreatedAt(LocalDateTime.now());
    return deckRepository.save(newDeck);
  }

  public void updateDeck(int deckId, DeckDto deck) {
    // Implementation for updating a deck goes here
    Deck existingDeck = deckRepository.findById(deckId).
        orElseThrow(() -> new NoSuchElementException("Deck with ID " + deckId + " not found."));

    verifyDeckOwnership(existingDeck);

    existingDeck.setName(deck.name());
    existingDeck.setUpdatedAt(LocalDateTime.now());
    existingDeck.setDescription(deck.description());

    deckRepository.save(existingDeck);
  }

  public void deleteDeck(int deckId) {
    Deck existingDeck = deckRepository.findById(deckId).
        orElseThrow(() -> new NoSuchElementException("Deck with ID " + deckId + " not found."));

    verifyDeckOwnership(existingDeck);

    deckRepository.delete(existingDeck);
  }

  private void verifyDeckOwnership(Deck deck) {
    if (!(deck.getOwner().getId() == userService.getAuthenticatedUser().getId())) {
      throw new SecurityException("You do not have permission to access this deck.");
    }
  }
}
