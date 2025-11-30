package com.github.esousacosta.ankiclone.services;

import com.github.esousacosta.ankiclone.models.card.Card;
import com.github.esousacosta.ankiclone.models.card.CardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CardService {
    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card getCardById(int id) throws NoSuchElementException{
        Card card = cardRepository.findById(id);
        if (card == null) {
            throw new NoSuchElementException("card with ID " + id + " not found.");
        }
        return card;
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }
}
