package com.ray.pomin.order.service;

import com.ray.pomin.order.Order;
import com.ray.pomin.order.OrderInfo;
import com.ray.pomin.order.repository.OrderRepository;
import com.ray.pomin.payment.domain.Payment;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Order payOrder(Long orderId, Payment payment) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 없습니다."));
        return order.paid(payment);
    }

    @Transactional
    public Order createOrder(Order order) {
        order.place();
        orderRepository.save(order);
        return order;
    }

    @Transactional
    public Order acceptOrder(Order order, OrderInfo orderInfo) {
        Order acceptedOrder = order.acceptOrder(orderInfo);
        return orderRepository.save(acceptedOrder);
    }

    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public Order getOrderByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderInfoOrderNumber(orderNumber)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 없습니다."));
    }

}
