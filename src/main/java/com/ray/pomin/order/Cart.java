package com.ray.pomin.order;

import org.aspectj.weaver.ast.Or;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Locale;

public record Cart(
        Long customerId,
        List<OrderItem> orderItems,
        Long storeId

) {
    public Order toOrder() {
        Order order = new Order().builder()
                .customerId(customerId)
                .orderItems(orderItems)
                .storeId(storeId)
                .build();
        return order;
    }
}
