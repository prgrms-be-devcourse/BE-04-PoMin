package com.ray.pomin.payment.controller.dto;

import com.ray.pomin.payment.domain.PayMethod;
import com.ray.pomin.payment.domain.PayType;
import com.ray.pomin.payment.domain.Payment;
import com.ray.pomin.payment.domain.PaymentStatus;
import org.springframework.security.core.parameters.P;

public record PaymentResponse(Long id, int amount, PaymentStatus status, PayMethod payMethod, PayType payType) {

    public PaymentResponse(Payment payment) {
        this(payment.getId(), payment.getAmount(), payment.getStatus(), payment.getPayInfo().getMethod(), payment.getPayInfo().getType());
    }
}
