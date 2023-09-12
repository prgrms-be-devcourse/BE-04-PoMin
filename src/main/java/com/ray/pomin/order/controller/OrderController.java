package com.ray.pomin.order.controller;

import com.ray.pomin.order.Cart;
import com.ray.pomin.order.Order;
import com.ray.pomin.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public Order saveOrder(@RequestBody Cart cart) {
        Order order = cart.toOrder();
        orderService.createOrder(order);
        payOrder(order);
        return order;
    }

    @GetMapping("/orders/{customerId}")
    public List<Order> getOrdersByCustomerId(@PathVariable Long customerId) {
        List<Order> orders = orderService.getOrdersByCustomerId(customerId);
        return orders;
    }

    private void payOrder(Order order) {
        orderService.payOrder(order.getId());
    }

}
