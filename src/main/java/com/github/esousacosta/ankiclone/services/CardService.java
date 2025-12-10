package com.github.esousacosta.ankiclone.services;

import com.github.esousacosta.ankiclone.models.card.Card;
import com.github.esousacosta.ankiclone.models.deck.Deck;
import com.github.esousacosta.ankiclone.models.dtos.CardDto;
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

    public Card saveCard(CardDto card) {
      //return cardRepository.save(card);
      // Placeholder implementation
      return new Card();
    }

    public Card updateCard(int cardId, CardDto card) {
      Card existingCard = cardRepository.findById(cardId)
          .orElseThrow(() -> new NoSuchElementException("Card with ID " + cardId + " not found."));
      verifyCardOwnership(existingCard);
      // Update fields of existingCard based on card DTO
      existingCard.setFront(card.front());
      existingCard.setBack(card.back());
      existingCard.setCategory(card.category());
      existingCard.setUpdatedAt(java.time.LocalDateTime.now());

      return cardRepository.save(existingCard);
    }

    public Card getCardById(int id) throws NoSuchElementException{
        Optional<Card> card = cardRepository.findById(id);
        if (card.isEmpty()) {
            throw new NoSuchElementException("card with ID " + id + " not found.");
        }

        verifyCardOwnership(card.get());

        return card.get();
    }

    public List<Card> getCardsByUsername(String username) throws NoSuchElementException {
      log.info("Fetching cards for username: {}", username);
      User user = userService.getUserByUsername(username);
      return cardRepository.findByUserId(user.getId());
    }

  public List<Card> getCardsByUser(User user) throws NoSuchElementException {
    log.info("Fetching cards for user: {}", user.getUsername());
    return cardRepository.findByUserId(user.getId());
  }

  public void deleteCardById(int id) {
      Card card = getCardById(id);
      cardRepository.delete(card);
  }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }


  private void verifyCardOwnership(Card card) {
    if (!(card.getUser().getId() == userService.getAuthenticatedUser().getId())) {
      throw new SecurityException("You do not have permission to access this card.");
    }
  }
}
