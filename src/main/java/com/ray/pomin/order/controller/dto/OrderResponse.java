package com.ray.pomin.order.controller.dto;

import com.ray.pomin.order.Order;
import com.ray.pomin.order.OrderInfo;
import com.ray.pomin.payment.domain.Payment;
import com.ray.pomin.store.domain.Store;

public record OrderResponse(
        Long id,
        Store store,
        OrderInfo orderInfo,
        int totalPrice,
        Payment payment
) {

    public OrderResponse(Order order) {
        this(
                order.getId(),
                order.getStore(),
                order.getOrderInfo(),
                order.getTotalPrice(),
                order.getPayment()
        );
    }

}
