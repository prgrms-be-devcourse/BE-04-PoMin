package com.ray.pomin.order.controller.dto;

import com.ray.pomin.order.Order;
import com.ray.pomin.order.OrderInfo;
import com.ray.pomin.payment.domain.Payment;
import com.ray.pomin.store.domain.Store;

public record OrderResponseDto(
        Long id,
        Store store,
        OrderInfo orderInfo,
        int totalPrice,
        Payment payment
) {

    public OrderResponseDto(Order order) {
        this(
                order.getId(),
                order.getStore(),
                order.getOrderInfo(),
                order.getTotalPrice(),
                order.getPayment()
        );
    }

}
