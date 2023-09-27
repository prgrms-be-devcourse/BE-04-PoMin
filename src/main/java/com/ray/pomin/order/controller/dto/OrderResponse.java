package com.ray.pomin.order.controller.dto;

import com.ray.pomin.order.Order;
import com.ray.pomin.order.OrderInfo;
import com.ray.pomin.payment.domain.Payment;
import com.ray.pomin.store.domain.Store;

public record OrderResponse(
        Long id,
        OrderInfo orderInfo,
        int totalPrice
) {

    public OrderResponse(Order order) {
        this(
                order.getId(),
                order.getOrderInfo(),
                order.getTotalPrice()
        );
    }

}
