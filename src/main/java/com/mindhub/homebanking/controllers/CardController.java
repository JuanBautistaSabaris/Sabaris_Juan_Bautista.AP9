package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.enums.CardColor;
import com.mindhub.homebanking.enums.CardType;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;

    public static int cvvGenerator() {
        Random random = new Random();
        return random.nextInt(900) + 100;
    }

    public static String cardNumberGenerator() {
        StringBuilder cardNumber = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                cardNumber.append(random.nextInt(10));
            }
            if (i < 3) {
                cardNumber.append("-");
            }
        }
        return cardNumber.toString();
    }

    @GetMapping("/cards")
    public List<CardDTO> getAllCards(){
        return cardRepository.findAll().stream().map(CardDTO::new).collect(toList());
    }

    @GetMapping("/clients/current/cards")
    public List<CardDTO> getCurrentClientCards(Authentication authentication){
        return clientRepository.findByEmail(authentication.getName()).getCards().stream().map(CardDTO::new).collect(toList());
    }


    @PostMapping(path = "/clients/current/cards")
    public ResponseEntity<Object> createCurrentCard(
            @RequestParam CardType cardType,
            @RequestParam CardColor cardColor,
            Authentication authentication) {

        boolean hasClientAuthority = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> authority.equals("CLIENT"));
        if (!hasClientAuthority) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        Client client = clientRepository.findByEmail(authentication.getName());

        if (cardRepository.existsByTypeAndColorAndClient(cardType, cardColor, client)) {
            return new ResponseEntity<>("card alredy exist", HttpStatus.FORBIDDEN);
        }

        Card newCard = new Card(client.cardHolder(), cardType, cardColor, cardNumberGenerator(), cvvGenerator(), LocalDateTime.now(), LocalDateTime.now().plusYears(5));
        client.addCard(newCard);
        cardRepository.save(newCard);
        clientRepository.save(client);
        return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }