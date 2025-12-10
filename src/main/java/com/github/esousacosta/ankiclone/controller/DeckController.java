package com.github.esousacosta.ankiclone.controller;

import com.github.esousacosta.ankiclone.models.deck.Deck;
import com.github.esousacosta.ankiclone.models.dtos.DeckDto;
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

import static com.github.esousacosta.ankiclone.utils.security.HelperTools.getAuthenticatedUserUsername;

@RestController
@Slf4j
@RequestMapping("/decks")
public class DeckController {
  private final DeckService deckService;
  private final UserService userService;

  public DeckController(DeckService deckService, UserService userService) {
    this.deckService = deckService;
    this.userService = userService;
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
}
