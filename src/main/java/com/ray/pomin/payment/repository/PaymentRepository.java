package com.ray.pomin.payment.repository;

import com.ray.pomin.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByPgInfoPayKey(String payKey);

}
