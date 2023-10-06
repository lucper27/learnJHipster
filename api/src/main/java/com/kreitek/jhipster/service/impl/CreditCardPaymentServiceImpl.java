package com.kreitek.jhipster.service.impl;

import com.kreitek.jhipster.domain.Payment;
import com.kreitek.jhipster.service.PaymentService;
import com.kreitek.jhipster.service.dto.PaymentDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("CreditCardPaymentServiceImpl")
public class CreditCardPaymentServiceImpl implements PaymentService {


    @Override
    public PaymentDTO makePayment(PaymentDTO payment) {
        // some method for specific payment
        return new PaymentDTO(null,null,null);
    }
}
