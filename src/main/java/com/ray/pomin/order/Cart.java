package com.ray.pomin.order;

import com.ray.pomin.store.domain.Store;

import java.util.List;

public record Cart(
        Long customerId,
        List<OrderItem> orderItems,
        Store store

) {
    public Order toOrder() {
        return new Order().builder()
                .customerId(customerId)
                .orderItems(orderItems)
                .store(store)
                .build();
    }

}
