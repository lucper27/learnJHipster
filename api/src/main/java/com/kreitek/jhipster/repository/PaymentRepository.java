package com.kreitek.jhipster.repository;

import com.kreitek.jhipster.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
