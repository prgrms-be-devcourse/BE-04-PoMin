package com.ray.pomin.order.controller;

import com.ray.pomin.global.auth.model.Claims;
import com.ray.pomin.global.auth.model.JwtUser;
import com.ray.pomin.order.Cart;
import com.ray.pomin.order.Order;
import com.ray.pomin.order.controller.dto.OrderResponse;
import com.ray.pomin.order.service.OrderService;
import com.ray.pomin.payment.domain.Payment;
import com.ray.pomin.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final PaymentService paymentService;

    @PostMapping("/orders")
    public OrderResponse saveOrder(@RequestBody Cart cart, @RequestParam String paymentKey) {
        Order order = cart.toOrder();
        orderService.createOrder(order);
        payOrder(order, paymentKey);
        return new OrderResponse(order);
    }


    @GetMapping("/orders")
    public List<OrderResponse> getOrdersByCustomerId(@AuthenticationPrincipal Claims claims) {
        List<Order> orders = orderService.getOrdersByCustomerId(claims.id());
        return orders.stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/orders/payOrder")
    public ResponseEntity<Void> create(@RequestParam String orderNumber,
                                       @RequestParam String paymentKey,
                                       @RequestParam int amount,
                                       @AuthenticationPrincipal JwtUser user) {
        Order order = orderService.getOrderByOrderNumber(orderNumber);
        Long paymentId = paymentService.create(orderNumber, paymentKey, amount, order.getCustomerId());
        payOrder(order, paymentKey);
        return ResponseEntity.created(URI.create("/api/v1/payments" + paymentId)).build();
    }

    private void payOrder(Order order, String paymentKey) {
        Payment payment = paymentService.findByPgInfoPayKey(paymentKey);
        orderService.payOrder(order.getId(), payment);
    }

}
