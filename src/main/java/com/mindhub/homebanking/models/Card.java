package com.mindhub.homebanking.models;

import com.mindhub.homebanking.enums.CardColor;
import com.mindhub.homebanking.enums.CardType;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String cardHolder;
    @Enumerated(EnumType.STRING)
    private CardType type;
    @Enumerated
    private CardColor color;
    private String number;
    private int cvv;
    private LocalDateTime fromDate;
    private LocalDateTime thruDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    public Card() {
    }

    public Card(String cardHolder, CardType type, CardColor color, String number, int cvv, LocalDateTime fromDate, LocalDateTime thruDate) {
        this.cardHolder = cardHolder;
        this.type = type;
        this.color = color;
        this.number = number;
        this.cvv = cvv;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
    }

    public long getId() { return id; }

    public String getCardHolder() { return cardHolder; }

    public CardType getType() { return type; }

    public CardColor getColor() { return color; }

    public String getNumber() { return number; }

    public int getCvv() { return cvv; }

    public LocalDateTime getFromDate() { return fromDate; }

    public LocalDateTime getThruDate() { return thruDate; }

    public Client getclient() { return client; }

    public void setCardHolder(String cardHolder) { this.cardHolder = cardHolder; }

    public void setType(CardType type) { this.type = type; }

    public void setColor(CardColor color) { this.color = color; }

    public void setNumber(String number) { this.number = number; }

    public void setCvv(int cvv) { this.cvv = cvv; }

    public void setFromDate(LocalDateTime fromDate) { this.fromDate = fromDate; }

    public void setThruDate(LocalDateTime thruDate) { this.thruDate = thruDate; }

    public void setClient(Client client) { this.client = client; }

}