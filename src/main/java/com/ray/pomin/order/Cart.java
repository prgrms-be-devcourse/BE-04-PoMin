package com.ray.pomin.order;

import java.util.List;

public record Cart(
        Long customerId,
        List<OrderItem> orderItems,
        Long storeId

) {
    public Order toOrder() {
        return new Order().builder()
                .customerId(customerId)
                .orderItems(orderItems)
                .storeId(storeId)
                .build();
    }
}
