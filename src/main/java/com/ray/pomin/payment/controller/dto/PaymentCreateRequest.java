package com.ray.pomin.payment.controller.dto;

import com.ray.pomin.payment.domain.PGType;
import com.ray.pomin.payment.domain.Payment;
import com.ray.pomin.payment.domain.PaymentStatus;

public record PaymentCreateRequest(Long id, int amount, PaymentStatus status, PGType provider) {

    public PaymentCreateRequest(Payment payment) {
        this(payment.getId(), payment.getAmount(), payment.getStatus(), payment.getPgInfo().getProvider());
    }

}
