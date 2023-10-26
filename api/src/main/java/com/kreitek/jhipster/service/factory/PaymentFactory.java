package com.kreitek.jhipster.service.factory;

import com.kreitek.jhipster.domain.enumeration.CardType;
import com.kreitek.jhipster.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class PaymentFactory {
    private static PaymentService debitCardPayment;
    private static PaymentService payPalPayment;
    private static PaymentService giftCardPayment;
    private static PaymentService creditCardPayment;


    public static PaymentService getPaymentMethod (CardType cardType) {

        switch (cardType) {
            case DEBIT_CARD: return debitCardPayment;
            case CREDIT_CARD: return creditCardPayment;
            case GIFT_CARD: return giftCardPayment;
            case PAYPAL: return payPalPayment;
            default: return null; // <- MAL, NUNCA SE DEVUELVE NULL
        }

    }


    @Autowired
    public void setDebitCardPayment(@Qualifier("DebitCardPaymentServiceImpl") PaymentService debitCardPayment) {
        PaymentFactory.debitCardPayment = debitCardPayment;
    }

    @Autowired
    public void setCreditCardPayment(@Qualifier("CreditCardPaymentServiceImpl") PaymentService creditCardPayment) {
        PaymentFactory.creditCardPayment = creditCardPayment;
    }

    @Autowired
    public void setGiftCardPayment(@Qualifier("GiftCardPaymentServiceImpl") PaymentService giftCardPayment) {
        PaymentFactory.giftCardPayment = giftCardPayment;
    }

    @Autowired
    public static void setPaypalPayment(@Qualifier("PaypalPaymentServiceImpl") PaymentService payPalPayment) {
        PaymentFactory.payPalPayment = payPalPayment;
    }
}
