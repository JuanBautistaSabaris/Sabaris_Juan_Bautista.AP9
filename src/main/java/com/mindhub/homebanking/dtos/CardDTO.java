package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.enums.CardColor;
import com.mindhub.homebanking.enums.CardType;

import java.time.LocalDateTime;

public class CardDTO {
    private long id;
    private String cardHolder;
    private CardType type;
    private CardColor color;
    private String number;
    private int cvv;
    private LocalDateTime fromDate;
    private LocalDateTime thruDate;

    public CardDTO() {
    }

    public CardDTO(Card card) {
        this.id  = card.getId();
        this.cardHolder = card.getCardHolder();
        this.type = card.getType();
        this.color = card.getColor();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.fromDate = card.getFromDate();
        this.thruDate = card.getThruDate();
    }

    public long getId() { return id; }

    public String getCardHolder() { return cardHolder; }

    public CardType getType() { return type; }

    public CardColor getColor() { return color; }

    public String getNumber() { return number; }

    public int getCvv() { return cvv; }

    public LocalDateTime getFromDate() { return fromDate; }

    public LocalDateTime getThruDate() { return thruDate; }
}