package com.github.esousacosta.ankiclone.services;

import com.github.esousacosta.ankiclone.models.card.Card;
import com.github.esousacosta.ankiclone.repositories.CardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CardService {
    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card getCardById(int id) throws NoSuchElementException{
        Optional<Card> card = cardRepository.findById(id);
        if (card.isEmpty()) {
            throw new NoSuchElementException("card with ID " + id + " not found.");
        }
        return card.get();
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }
}
