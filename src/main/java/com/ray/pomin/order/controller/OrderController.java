package com.ray.pomin.order.controller;


import com.ray.pomin.global.auth.model.Claims;
import com.ray.pomin.order.Cart;
import com.ray.pomin.order.Order;
import com.ray.pomin.order.OrderInfo;
import com.ray.pomin.order.controller.dto.OrderRequest;
import com.ray.pomin.order.controller.dto.OrderResponse;
import com.ray.pomin.order.service.OrderService;
import com.ray.pomin.payment.domain.Payment;
import com.ray.pomin.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final PaymentService paymentService;

    @ResponseBody
    @PostMapping
    public OrderResponse saveOrder(@RequestBody Cart cart) {
        Order order = cart.toOrder();
        orderService.createOrder(order);
        System.out.println(order.getOrderItems());
        return new OrderResponse(order);
    }

    @ResponseBody
    @GetMapping
    public List<OrderResponse> getOrdersByCustomerId(@AuthenticationPrincipal Claims claims) {
        List<Order> orders = orderService.getOrdersByCustomerId(claims.id());
        return orders.stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
    }

    @ResponseBody
    @GetMapping("/payOrder")
    public ResponseEntity<Void> create(@RequestParam String orderId,
                                       @RequestParam String paymentKey,
                                       @RequestParam int amount) {
        Order order = orderService.getOrderByOrderNumber(orderId);
        Long paymentId = paymentService.create(orderId, paymentKey, amount, order.getCustomerId());
        payOrder(order, paymentKey);
        return ResponseEntity.created(URI.create("/api/v1/payments/" + paymentId)).build();
    }

    @ResponseBody
    @PostMapping("/{orderNumber}")
    public ResponseEntity<OrderRequest> acceptOrder(@PathVariable String orderNumber, @RequestBody OrderRequest orderRequest) {
        Order order = orderService.getOrderByOrderNumber(orderNumber);
        OrderInfo orderInfo = orderRequest.createOrderInfo(orderNumber);
        orderService.acceptOrder(order, orderInfo);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    private void payOrder(Order order, String paymentKey) {
        Payment payment = paymentService.findByPgInfoPayKey(paymentKey);
        orderService.payOrder(order.getId(), payment);
    }

    @GetMapping("/pay-page")
    public String payPage(@RequestParam String orderNumber, Model model) {
        Order order = orderService.getOrderByOrderNumber(orderNumber);
        model.addAttribute("order", new OrderResponse(order));

        return "pay-page";
    }

}
