package com.github.esousacosta.ankiclone.services;

import com.github.esousacosta.ankiclone.data.enums.ReviewQuality;
import com.github.esousacosta.ankiclone.data.models.card.Card;
import com.github.esousacosta.ankiclone.data.models.deck.Deck;
import com.github.esousacosta.ankiclone.data.models.dtos.CardDto;
import com.github.esousacosta.ankiclone.data.models.user.User;
import com.github.esousacosta.ankiclone.repositories.CardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.time.LocalDateTime;

@Service
@Slf4j
public class CardService {
    private final CardRepository cardRepository;
    private final UserService userService;
    private final DeckService deckService;
    private final SpacedRepetitionService spacedRepetitionService;

    public CardService(CardRepository cardRepository, UserService userService, DeckService deckService, SpacedRepetitionService spacedRepetitionService) {
        this.cardRepository = cardRepository;
        this.userService = userService;
        this.deckService = deckService;
        this.spacedRepetitionService = spacedRepetitionService;
    }

    public Card saveCard(CardDto card) {
      //return cardRepository.save(card);
      // Placeholder implementation
      Card newCard = new Card();
      newCard.setFront(card.front());
      newCard.setBack(card.back());
      newCard.setCategory(card.category());
      newCard.setUser(userService.getAuthenticatedUser());
      newCard.setCreatedAt(LocalDateTime.now());
      newCard.setDeck(deckService.getDeckById(card.deckId()));
      newCard.setNextReviewDate(LocalDateTime.now());
      return cardRepository.save(newCard);
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
      log.info("User '{}' deleted card with ID {}.",
          userService.getAuthenticatedUser().getUsername(),
          id);
  }

  public void reviewCard(int cardId, int quality) {
      Card card = getCardById(cardId);
      spacedRepetitionService.processCardReview(card, quality);
      cardRepository.save(card);
      log.info("User '{}' reviewed card with ID {} with quality {}.",
          userService.getAuthenticatedUser().getUsername(),
          cardId,
          quality);
  }

  public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

  /**
   * Get all cards that are due for review (nextReviewDate <= now) for the authenticated user.
   * This is useful for showing the user which cards they should review.
   */
  public List<Card> getCardsDueForReview() {
    User user = userService.getAuthenticatedUser();
    return cardRepository.findByUserIdAndNextReviewDateLessThanEqual(
        user.getId(),
        LocalDateTime.now()
    );
  }

  /**
   * Get all cards due for review for a specific user.
   */
  public List<Card> getCardsDueForReview(User user) {
    return cardRepository.findByUserIdAndNextReviewDateLessThanEqual(
        user.getId(),
        LocalDateTime.now()
    );
  }

  public List<Card> getCardsDueForReviewInDeck(int deckId) {
    Deck deck = deckService.getDeckById(deckId);
    deckService.verifyDeckOwnership(deck);
    return cardRepository.findByDeckIdAndNextReviewDateLessThanEqual(
        deckId,
        LocalDateTime.now()
    );
  }


  private void verifyCardOwnership(Card card) {
    if (card.getUser().getId() != userService.getAuthenticatedUser().getId()) {
      throw new SecurityException("You do not have permission to access this card.");
    }
  }
}
