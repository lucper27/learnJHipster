package com.kreitek.jhipster.web.rest;

import com.kreitek.jhipster.domain.enumeration.CardType;
import com.kreitek.jhipster.service.PaymentService;
import com.kreitek.jhipster.service.dto.PaymentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PaymentResource {

    private final Logger log = LoggerFactory.getLogger(AlbumResource.class);

    @Qualifier("CreditCardPaymentServiceImpl")
    private final PaymentService creditCardPayment;

    @Qualifier("DebitCardPaymentServiceImpl")
    private final PaymentService debitCardPayment;

    @Qualifier("PaypalPaymentServiceImpl")
    private final PaymentService payPalPayment;

    @Qualifier("GiftCardPaymentServiceImpl")
    private final PaymentService giftCardPayment;

    public PaymentResource(@Qualifier("CreditCardPaymentServiceImpl") PaymentService creditCardPayment,
                           @Qualifier("DebitCardPaymentServiceImpl") PaymentService debitCardPayment,
                           @Qualifier("PaypalPaymentServiceImpl") PaymentService payPalPayment,
                           @Qualifier("GiftCardPaymentServiceImpl") PaymentService giftCardPayment) {
        this.creditCardPayment = creditCardPayment;
        this.debitCardPayment = debitCardPayment;
        this.payPalPayment = payPalPayment;
        this.giftCardPayment = giftCardPayment;
    }


    @PostMapping("/pay")
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentDTO paymentDTO) {
        log.debug("Rest request to make payment -> {}", paymentDTO);
        PaymentDTO result = new PaymentDTO();
        String cardType = paymentDTO.getCardType();

        if (cardType.equals(CardType.CREDIT_CARD)) {
            result = creditCardPayment.makePayment(paymentDTO);
        } else if (cardType.equals(CardType.DEBIT_CARD)) {
            result = debitCardPayment.makePayment(paymentDTO);
        } else if (cardType.equals(CardType.GIFT_CARD)) {
            result = giftCardPayment.makePayment(paymentDTO);
        } else if (cardType.equals(CardType.PAYPAL)) {
            result = payPalPayment.makePayment(paymentDTO);
        }

        return ResponseEntity.ok().body(result);
    }
}
