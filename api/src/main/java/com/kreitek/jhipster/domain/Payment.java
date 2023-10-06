package com.kreitek.jhipster.domain;

import com.kreitek.jhipster.domain.enumeration.CardType;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "payment")
public class Payment {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "card_number")
    private Long cardNumber;
    @Enumerated(EnumType.STRING)
    @Column(name = "card_type")
    private CardType cardType;

    @Column(name = "payed_at")
    private Instant payedAt;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
