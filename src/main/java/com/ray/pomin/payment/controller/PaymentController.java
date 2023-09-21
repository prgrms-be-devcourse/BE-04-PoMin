package com.ray.pomin.payment.controller;

import com.ray.pomin.global.auth.model.JwtUser;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/payment")
    public String payPage(@RequestParam String orderId, Model model) {
        // orderService 에서 orderId로 조회해서 model 에 담아서 전달
        return "pay-test-page";
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
