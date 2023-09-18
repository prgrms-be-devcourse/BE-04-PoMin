package com.ray.pomin.payment.controller.dto;

import com.ray.pomin.payment.domain.PGInfo;
import com.ray.pomin.payment.domain.PayInfo;
import com.ray.pomin.payment.domain.PayMethod;
import com.ray.pomin.payment.domain.PayType;
import com.ray.pomin.payment.domain.Payment;
import com.ray.pomin.payment.domain.PaymentStatus;

import java.time.LocalDateTime;

import static com.ray.pomin.payment.domain.PGType.TOSS;
import static com.ray.pomin.payment.domain.PaymentStatus.COMPLETE;

public record PaymentInfo( int amount, PaymentStatus status, String paymentKey, PayMethod payMethod, PayType payType, LocalDateTime approvedAt) {

    public Payment toEntity() {
        return Payment.builder()
                .amount(amount)
                .status(COMPLETE)
                .pgInfo(new PGInfo(TOSS, paymentKey))
                .payInfo(new PayInfo(payMethod, payType))
                .approvedAt(approvedAt)
                .build();
    }

}
