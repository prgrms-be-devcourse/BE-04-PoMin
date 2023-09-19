package com.ray.pomin.order.controller;

import com.ray.pomin.order.controller.dto.OrderRequest;
import com.ray.pomin.global.auth.model.Claims;
import com.ray.pomin.order.Cart;
import com.ray.pomin.order.Order;
import com.ray.pomin.order.OrderInfo;
import com.ray.pomin.order.controller.dto.OrderResponse;
import com.ray.pomin.order.service.OrderService;
import com.ray.pomin.payment.domain.Payment;
import com.ray.pomin.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final PaymentService paymentService;

    @PostMapping()
    public OrderResponse saveOrder(@RequestBody Cart cart, @RequestParam String paymentKey) {
        Order order = cart.toOrder();
        orderService.createOrder(order);
        payOrder(order, paymentKey);
        return new OrderResponse(order);
    }


    @GetMapping()
    public List<OrderResponse> getOrdersByCustomerId(@AuthenticationPrincipal Claims claims) {
        List<Order> orders = orderService.getOrdersByCustomerId(claims.id());
        return orders.stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/payOrder")
    public ResponseEntity<Void> create(@RequestParam String orderNumber,
                                       @RequestParam String paymentKey,
                                       @RequestParam int amount) {
        Long paymentId = paymentService.create(orderNumber, paymentKey, amount);
        Order order = orderService.getOrderByOrderNumber(orderNumber);
        payOrder(order, paymentKey);
        return ResponseEntity.created(URI.create("/api/v1/payments" + paymentId)).build();
    }

    @PostMapping("{orderNumber}")
    public ResponseEntity<OrderRequest> acceptOrder(@PathVariable String orderNumber, @RequestBody OrderRequest orderRequest) {
        Order order = orderService.getOrderByOrderNumber(orderNumber);
        OrderInfo orderInfo = OrderInfo.acceptOrderInfo(orderNumber, orderRequest);
        orderService.acceptOrder(order, orderInfo);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private void payOrder(Order order, String paymentKey) {
        Payment payment = paymentService.findByPgInfoPayKey(paymentKey);
        orderService.payOrder(order.getId(), payment);
    }

}
