package com.ray.pomin.order.controller.dto;

import com.ray.pomin.customer.domain.PhoneNumber;
import com.ray.pomin.payment.domain.PGType;
import com.ray.pomin.payment.domain.PaymentStatus;

import java.time.LocalDateTime;

public record OrderResponseDto(
        Long id,
        Long storeId,
        String orderNumber,
        String requestedDetails,
        int totalPrice,
        PhoneNumber customerPhoneNumber,
        LocalDateTime reservationTime,
        PaymentResponseDto payment
) {
    public record PaymentResponseDto(
            Long id,
            int amount,
            PaymentStatus status,
            PGType provider
    ) {}

}
