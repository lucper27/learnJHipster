package com.kreitek.jhipster.service.dto;


import java.time.Instant;

public class PaymentDTO {

    private Long cardNumber;

    private String cardType;

    private Instant payedAt;

    public PaymentDTO() {
    }

    public PaymentDTO(Long cardNumber, String cardType, Instant payedAt) {
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

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Instant getPayedAt() {
        return payedAt;
    }

    public void setPayedAt(Instant payedAt) {
        this.payedAt = payedAt;
    }
}
