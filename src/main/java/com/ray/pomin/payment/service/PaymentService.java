package com.ray.pomin.payment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ray.pomin.payment.controller.dto.PaymentInfo;
import com.ray.pomin.payment.domain.Payment;
import com.ray.pomin.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

  private final PaymentRepository paymentRepository;

  private final PaymentGatewayHandler paymentGatewayHandler;

  public Long create(String orderId, String paymentKey, int amount) throws JsonProcessingException {
    PaymentInfo paymentInfo = paymentGatewayHandler.makePaymentRequest(orderId, paymentKey, amount);
    Payment payment = paymentInfo.toEntity();

    return paymentRepository.save(payment).getId();
  }

  public void cancel(Payment payment) throws JsonProcessingException {
    PaymentInfo paymentInfo = paymentGatewayHandler.cancelPaymentRequest(payment);
    paymentRepository.save(payment.cancel(paymentInfo));
  }

  @Transactional(readOnly = true)
  public Payment findOne(Long paymentId) {
    return paymentRepository.findById(paymentId)
            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 결제건 입니다."));
  }

}
