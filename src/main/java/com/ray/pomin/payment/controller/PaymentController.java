package com.ray.pomin.payment.controller;

import com.ray.pomin.global.auth.model.JwtUser;
import com.ray.pomin.order.Order;
import com.ray.pomin.order.controller.dto.OrderResponse;
import com.ray.pomin.order.service.OrderService;
import com.ray.pomin.payment.controller.dto.PaymentFailResponse;
import com.ray.pomin.payment.controller.dto.PaymentResponse;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    private final OrderService orderService;

    @GetMapping("/payment")
    public String payPage(@RequestParam String orderNumber, Model model) {
        Order order = orderService.getOrderByOrderNumber(orderNumber);
        model.addAttribute("order", new OrderResponse(order));

        return "pay-page";
    }

    @ResponseBody
    @GetMapping("/payment/fail")
    public ResponseEntity<PaymentFailResponse> fail(@RequestParam String code,
                                                    @RequestParam String message,
                                                    @RequestParam String orderId) {
        return new ResponseEntity<>(new PaymentFailResponse(message, orderId), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @GetMapping("/payments/{paymentId}")
    public PaymentResponse find(@PathVariable Long paymentId, @AuthenticationPrincipal JwtUser user) {
        Payment payment = paymentService.findOneCheckingAuth(paymentId, user.userId());

        return new PaymentResponse(payment);
    }

}
