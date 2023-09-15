package com.ray.pomin.order.controller;

import com.ray.pomin.global.auth.model.Claims;
import com.ray.pomin.order.Cart;
import com.ray.pomin.order.Order;
import com.ray.pomin.order.controller.dto.OrderResponse;
import com.ray.pomin.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public OrderResponse saveOrder(@RequestBody Cart cart) {
        Order order = cart.toOrder();
        orderService.createOrder(order);
        payOrder(order);
        return new OrderResponse(order);
    }

    @GetMapping("/orders")
    public List<OrderResponse> getOrdersByCustomerId(@AuthenticationPrincipal Claims claims) {
        List<Order> orders = orderService.getOrdersByCustomerId(claims.id());
        return orders.stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
    }

    private void payOrder(Order order) {
        orderService.payOrder(order.getId());
    }

}
