package com.github.esousacosta.ankiclone.controller;

import com.github.esousacosta.ankiclone.models.card.Card;
import com.github.esousacosta.ankiclone.models.dtos.CardDto;
import com.github.esousacosta.ankiclone.services.CardService;
import com.github.esousacosta.ankiclone.utils.security.HelperTools;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
      String username = HelperTools.getAuthenticatedUserUsername();
      return cardService.getCardsByUsername(username);
    }

    @GetMapping(path = "{id}")
    public Card getCardById(@PathVariable int id) {
        return cardService.getCardById(id);
    }

    @PostMapping
    public Card createCard(@RequestBody @Valid CardDto card) {
        return cardService.saveCard(card);
    }
}
