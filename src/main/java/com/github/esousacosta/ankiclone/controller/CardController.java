package com.github.esousacosta.ankiclone.controller;

import com.github.esousacosta.ankiclone.models.card.Card;
import com.github.esousacosta.ankiclone.models.dtos.CardDto;
import com.github.esousacosta.ankiclone.services.CardService;
import com.github.esousacosta.ankiclone.services.UserService;
import com.github.esousacosta.ankiclone.utils.security.HelperTools;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/cards")
public class CardController {
    private final CardService cardService;
    private final UserService userService;

    public CardController(CardService cardService, UserService userService) {
        this.userService = userService;
        this.cardService = cardService;
    }

    @GetMapping
    public List<Card> getAllCardsForAuthenticatedUser() {
      return cardService.getCardsByUser(userService.getAuthenticatedUser());
    }

    @GetMapping(path = "/{id}")
    public Card getCardById(@PathVariable int id) {
        return cardService.getCardById(id);
    }

    @PostMapping
    public Card createCard(@RequestBody @Valid CardDto card) {
        return cardService.saveCard(card);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<String> updateCard(@PathVariable int id, @RequestBody @Valid CardDto card) {
      updateCard(id, card);
      return ResponseEntity.ok("Card updated successfully.");
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteCard(@PathVariable int id) {
        cardService.deleteCardById(id);
        return ResponseEntity.ok("Card deleted successfully.");
    }
}
