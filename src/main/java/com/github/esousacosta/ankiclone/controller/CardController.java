package com.github.esousacosta.ankiclone.controller;

import com.github.esousacosta.ankiclone.models.card.Card;
import com.github.esousacosta.ankiclone.services.CardService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/cards")
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public List<Card> getAllCards() {
      String username = SecurityContextHolder.getContext().getAuthentication().getName();
      return cardService.getCardsByUsername(username);
    }

    @GetMapping(path = "{id}")
    public Card getCardById(@PathVariable int id) {
        return cardService.getCardById(id);
    }
}
