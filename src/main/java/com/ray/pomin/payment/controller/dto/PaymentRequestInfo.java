package com.ray.pomin.payment.controller.dto;

public record PaymentRequestInfo(String orderId, String paymentKey, int amount, Long customerId) {

}
