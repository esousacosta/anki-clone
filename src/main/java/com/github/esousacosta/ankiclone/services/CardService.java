package com.github.esousacosta.ankiclone.services;

import com.github.esousacosta.ankiclone.models.card.Card;
import com.github.esousacosta.ankiclone.models.user.User;
import com.github.esousacosta.ankiclone.repositories.CardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class CardService {
    private final CardRepository cardRepository;
    private final UserService userService;

    public CardService(CardRepository cardRepository, UserService userService) {
        this.cardRepository = cardRepository;
        this.userService = userService;
    }

    public Card saveCard(Card card) {
      return cardRepository.save(card);
    }

    public Card getCardById(int id) throws NoSuchElementException{
        Optional<Card> card = cardRepository.findById(id);
        if (card.isEmpty()) {
            throw new NoSuchElementException("card with ID " + id + " not found.");
        }
        return card.get();
    }

    public List<Card> getCardsByUsername(String username) throws NoSuchElementException {
      log.info("Fetching cards for username: {}", username);
      User user = userService.getUserByUsername(username);
      return cardRepository.findByUserId(user.getId());
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }
}
