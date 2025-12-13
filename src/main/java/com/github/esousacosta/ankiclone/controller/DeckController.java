package com.github.esousacosta.ankiclone.controller;

import com.github.esousacosta.ankiclone.data.models.card.Card;
import com.github.esousacosta.ankiclone.data.models.deck.Deck;
import com.github.esousacosta.ankiclone.data.models.dtos.DeckDto;
import com.github.esousacosta.ankiclone.services.CardService;
import com.github.esousacosta.ankiclone.services.DeckService;
import com.github.esousacosta.ankiclone.services.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/decks")
public class DeckController {
  private final DeckService deckService;
  private final UserService userService;
  private final CardService cardService;

  public DeckController(DeckService deckService, UserService userService, CardService cardService) {
    this.deckService = deckService;
    this.userService = userService;
    this.cardService = cardService;
  }

  @GetMapping
  public List<Deck> getUserDecks() {
    return deckService.getDecksByOwnerId(userService.getAuthenticatedUser().getId());
  }

  @PostMapping
  public ResponseEntity<Deck> createDeck(@RequestBody @Valid DeckDto deck) {
    Deck newDeck = deckService.createDeck(deck);
    return ResponseEntity.created(null).body(newDeck);
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateDeck(@RequestBody @Valid DeckDto deck, @PathVariable int id) {
    // Implementation for updating a deck goes here
    deckService.updateDeck(id, deck);
    return ResponseEntity.ok("Deck updated successfully.");
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteDeck(@PathVariable int id) {
    // Implementation for deleting a deck goes here
    deckService.deleteDeck(id);
    return ResponseEntity.ok("Deck deleted successfully.");
  }

  @GetMapping("/{id}/review")
  public ResponseEntity<List<Card>> getCardsForReview(@PathVariable int id) {
    List<Card> cardsForReview = cardService.getCardsDueForReviewInDeck(id);
    return ResponseEntity.ok(cardsForReview);
  }
}
