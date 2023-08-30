package com.ray.pomin.order.service;

import com.ray.pomin.order.domain.Order;
import com.ray.pomin.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order findOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(
                        () -> new IllegalArgumentException("해당 주문이 없습니다.")
                );

        return order;
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);

    }

}
