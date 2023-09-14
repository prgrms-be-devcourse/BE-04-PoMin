package com.ray.pomin.payment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ray.pomin.payment.controller.dto.PaymentCancelResponse;
import com.ray.pomin.payment.controller.dto.PaymentFailResponse;
import com.ray.pomin.payment.controller.dto.PaymentResponse;
import com.ray.pomin.payment.domain.Payment;
import com.ray.pomin.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URI;

@Slf4j
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
  @GetMapping("/payment/success")
  public ResponseEntity<Void> create(@RequestParam String orderId,
                                            @RequestParam String paymentKey,
                                            @RequestParam int amount) throws JsonProcessingException {
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

  @PatchMapping("/payments/{paymentId}")
  public ResponseEntity<PaymentCancelResponse> cancel(@PathVariable Long paymentId) {
    Payment paymentToCancel = paymentService.findOne(paymentId);
    PaymentCancelResponse cancelResponse = paymentService.cancel(paymentToCancel);

    return ResponseEntity.status(HttpStatus.valueOf(cancelResponse.code()))
            .body(cancelResponse);
  }

  @GetMapping("/payments/{paymentId}")
  public PaymentResponse find(@PathVariable Long paymentId) {
    Payment payment = paymentService.findOne(paymentId);

    return new PaymentResponse(payment);
  }

}
