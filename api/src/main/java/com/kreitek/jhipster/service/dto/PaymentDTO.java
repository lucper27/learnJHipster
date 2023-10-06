package com.kreitek.jhipster.service.dto;


import com.kreitek.jhipster.domain.enumeration.CardType;

import java.time.Instant;

public class PaymentDTO {

    private Long cardNumber;

    private CardType cardType;

    private Instant payedAt;

    public PaymentDTO() {
    }

    public PaymentDTO(Long cardNumber, CardType cardType, Instant payedAt) {
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.payedAt = payedAt;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public Instant getPayedAt() {
        return payedAt;
    }

    public void setPayedAt(Instant payedAt) {
        this.payedAt = payedAt;
    }
}
