package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.enums.CardColor;
import com.mindhub.homebanking.enums.CardType;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CardServiceImplement implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public List<CardDTO> getAllCards() {
        return cardRepository.findAll().stream().map(CardDTO::new).collect(toList());
    }

    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }

    @Override
    public Card findById(long id) {
        return cardRepository.findById(id).orElse(null);
    }

    @Override
    public boolean existsByTypeAndColorAndClient(CardType cardType, CardColor cardColor, Client client) {
        return cardRepository.existsByTypeAndColorAndClient(cardType,cardColor,client);
    }

    @Override
    public Card createCard(String cardHolder, CardType cardType, CardColor cardColor) {
        return new Card(cardHolder, cardType, cardColor, this.generateNumber(), this.generateCvv(), LocalDateTime.now(), LocalDateTime.now().plusYears(5));
    }

    @Override
    public int generateCvv() {
        return (int) (Math.random() * 999);
    }

    @Override
    public String generateNumber() {
        DecimalFormat format = new DecimalFormat("0000");
        String number = "";
        for (int i = 0; i < 4; i++) {
            number += format.format((int) (Math.random() * 9999));
            if (i != 3) {
                number += "-";
            }
        }
        return number;
    }
}
