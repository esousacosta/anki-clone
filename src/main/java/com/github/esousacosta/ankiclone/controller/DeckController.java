package com.github.esousacosta.ankiclone.controller;

import com.github.esousacosta.ankiclone.models.deck.Deck;
import com.github.esousacosta.ankiclone.services.DeckService;
import com.github.esousacosta.ankiclone.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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


}
