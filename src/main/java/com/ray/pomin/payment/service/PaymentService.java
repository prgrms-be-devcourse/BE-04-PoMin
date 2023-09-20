package com.ray.pomin.payment.service;

import com.ray.pomin.payment.controller.dto.PaymentRequestInfo;
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

    public Long create(String orderId, String paymentKey, int amount, Long customerId) {
        Payment completedPayment = paymentGatewayHandler.makePaymentRequest(new PaymentRequestInfo(orderId, paymentKey, amount, customerId));

        return paymentRepository.save(completedPayment).getId();
    }

    public void cancel(Long paymentId) {
        Payment paymentToCancel = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 결제건 입니다"));
        Payment canceledPayment = paymentGatewayHandler.cancelPaymentRequest(paymentToCancel);

        paymentRepository.save(canceledPayment);
    }

    @Transactional(readOnly = true)
    public Payment findOne(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 결제건 입니다."));
    }

    @Transactional(readOnly = true)
    public Payment findOneCheckingAuth(Long paymentId, Long customerId) {
        return paymentRepository.findById(paymentId)
                .filter(payment -> payment.getCustomerId().equals(customerId))
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 결제건 입니다."));
    }

    @Transactional(readOnly = true)
    public Payment findByPgInfoPayKey(String payKey) {
        return paymentRepository.findByPgInfoPayKey(payKey)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 결제건 입니다."));
    }

}
