package com.ray.pomin.payment.controller;

import com.ray.pomin.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URI;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PaymentController {

  @Value("${toss.api.testSecretApiKey}")
  private String secretKey;

  private final PaymentService paymentService;

  @GetMapping("/payment")
  public String payPage(@RequestParam String orderId, Model model) {
    // orderService 에서 orderId로 조회해서 model 에 담아서 전달
    return "pay-test-page";
  }

  @ResponseBody
  @GetMapping("/tosspayments/success")
  public ResponseEntity<Void> validPaymentRequest(@RequestParam String orderId,
                                            @RequestParam String paymentKey,
                                            @RequestParam int amount) {
    Long paymentId = paymentService.doFinalPaymentRequest(orderId, paymentKey, amount);

    return ResponseEntity.created(URI.create("/api/v1/payments" + paymentId)).build();
  }
}
