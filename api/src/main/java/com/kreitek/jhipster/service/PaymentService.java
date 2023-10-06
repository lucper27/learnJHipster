package com.kreitek.jhipster.service;

import com.kreitek.jhipster.domain.Payment;
import com.kreitek.jhipster.service.dto.PaymentDTO;

public interface PaymentService {

    PaymentDTO makePayment(PaymentDTO payment);
}
