package com.github.esousacosta.ankiclone.services;

import com.github.esousacosta.ankiclone.models.card.Card;
import com.github.esousacosta.ankiclone.models.card.CardRepository;
import org.springframework.stereotype.Service;

@Service
public class CardService {
    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card getCardById(int id) {
        return cardRepository.findById(id);
    }
}
