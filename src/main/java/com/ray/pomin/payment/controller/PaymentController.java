package com.ray.pomin.payment.controller;

import com.ray.pomin.payment.controller.dto.PaymentFailResponse;
import com.ray.pomin.payment.controller.dto.PaymentResponse;
import com.ray.pomin.payment.domain.Payment;
import com.ray.pomin.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  @GetMapping("/payment/success")
  public ResponseEntity<Void> create(@RequestParam String orderId,
                                            @RequestParam String paymentKey,
                                            @RequestParam int amount) {
    Long paymentId = paymentService.doFinalPaymentRequest(orderId, paymentKey, amount);

    return ResponseEntity.created(URI.create("/api/v1/payments" + paymentId)).build();
  }

  @ResponseBody
  @GetMapping("/payment/fail")
  public ResponseEntity<PaymentFailResponse> fail(@RequestParam String code,
                                                  @RequestParam String message,
                                                  @RequestParam String orderId) {

    return new ResponseEntity<>(new PaymentFailResponse(message, orderId), HttpStatus.valueOf(code));
  }

  @GetMapping("/api/v1/payments/{paymentId}/cancel")
  public ResponseEntity<Void> cancel(@PathVariable Long paymentId) {
    // 결제가 묶여 있는 주문의 상태가 결제취소가 가능한 상태인지 확인하는 로직
    paymentService.cancel(paymentId);

    return ResponseEntity.ok().build();
  }

  @GetMapping("/api/v1/payments/{paymentId}")
  public PaymentResponse find(@PathVariable Long paymentId) {
    Payment payment = paymentService.findOne(paymentId);

    return new PaymentResponse(payment);
  }

}
