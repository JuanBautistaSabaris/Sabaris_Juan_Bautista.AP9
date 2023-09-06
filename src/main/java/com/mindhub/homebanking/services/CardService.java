package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.enums.CardColor;
import com.mindhub.homebanking.enums.CardType;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface CardService {
    List<CardDTO> getAllCards();
    void saveCard(Card card);
    Card findById(long id);
    boolean existsByTypeAndColorAndClient( CardType cardType,CardColor cardColor,Client client);
    Card createCard(String cardHolder, CardType cardType, CardColor cardColor);
    int generateCvv();
    String generateNumber();
}
