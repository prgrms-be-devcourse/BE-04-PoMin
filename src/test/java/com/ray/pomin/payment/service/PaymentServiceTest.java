package com.ray.pomin.payment.service;

import com.ray.pomin.payment.controller.dto.PaymentRequestInfo;
import com.ray.pomin.payment.domain.PGInfo;
import com.ray.pomin.payment.domain.PayInfo;
import com.ray.pomin.payment.domain.Payment;
import com.ray.pomin.payment.domain.PaymentStatus;
import com.ray.pomin.payment.repository.PaymentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.ray.pomin.payment.domain.PGType.TOSS;
import static com.ray.pomin.payment.domain.PayMethod.CARD;
import static com.ray.pomin.payment.domain.PayType.KB;
import static com.ray.pomin.payment.domain.PaymentStatus.COMPLETE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentGatewayHandler pgHandler;

    @InjectMocks
    private PaymentService paymentService;

    final Long paymentId = 1L;

    final String orderId = "randomw342orderc23ode";

    final String paymentKey = "payKeyProvidedByPG33";

    final int amount = 10000;

    final LocalDateTime approvedAt = LocalDateTime.now().minusSeconds(3);

    final Long customerId = 2L;

    final Payment payment = Payment.builder()
            .amount(amount)
            .status(COMPLETE)
            .pgInfo(new PGInfo(TOSS, paymentKey))
            .payInfo(new PayInfo(CARD, KB))
            .approvedAt(approvedAt)
            .customerId(customerId)
            .build();

    final Payment savedPayment = Payment.builder()
            .id(1L)
            .amount(payment.getAmount())
            .status(payment.getStatus())
            .pgInfo(new PGInfo(payment.getPgInfo().getProvider(), payment.getPgInfo().getPayKey()))
            .payInfo(new PayInfo(payment.getPayInfo().getMethod(), payment.getPayInfo().getType()))
            .approvedAt(payment.getApprovedAt())
            .customerId(customerId)
            .build();

    final Payment canceledPayment = Payment.builder()
            .id(1L)
            .amount(payment.getAmount())
            .status(PaymentStatus.CANCELED)
            .pgInfo(new PGInfo(payment.getPgInfo().getProvider(), payment.getPgInfo().getPayKey()))
            .payInfo(new PayInfo(payment.getPayInfo().getMethod(), payment.getPayInfo().getType()))
            .approvedAt(payment.getApprovedAt())
            .customerId(customerId)
            .build();

    @Test
    @DisplayName("승인된 결제를 저장한 후 id값을 반환한다")
    public void successCreateApprovedPayment() {

        given(pgHandler.makePaymentRequest(new PaymentRequestInfo(orderId, paymentKey, amount, 2L)))
                .willReturn(payment);

        given(paymentRepository.save(any(Payment.class))).willReturn(savedPayment);

        //when
        Long paymentId = paymentService.create(orderId, paymentKey, amount, 2L);

        //then
        assertEquals(paymentId, savedPayment.getId());
    }

    @Test
    @DisplayName("결제를 취소할 수 있다")
    public void successCancelPayment() {
        //given
        given(pgHandler.cancelPaymentRequest(any(Payment.class))).willReturn(canceledPayment);
        given(paymentRepository.findById(1L)).willReturn(Optional.ofNullable(savedPayment));
        given(paymentRepository.save(any(Payment.class))).willReturn(canceledPayment);

        //when
        paymentService.cancel(1L);

        //then
        verify(paymentRepository).save(canceledPayment);
    }

    @Test
    @DisplayName("사용자는 자신의 결제 조회에 성공한다")
    public void successFindOwnPayment() {
        //given
        given(paymentRepository.findById(paymentId)).willReturn(Optional.ofNullable(savedPayment));

        //when
        Payment myPayment = paymentService.findOneCheckingAuth(paymentId, 2L);

        //then
        assertEquals(myPayment.getId(), paymentId);
    }

    @Test
    @DisplayName("사용자는 다른 사용자의 결제 조회에 실패한다")
    public void failFindOthersPayment() {
        Long otherCustomersId = 6L;

        //given
        given(paymentRepository.findById(paymentId)).willReturn(Optional.ofNullable(savedPayment));

        //when //then
        assertThrows(NoSuchElementException.class, () -> paymentService.findOneCheckingAuth(paymentId, otherCustomersId));
    }

    @Test
    @DisplayName("결제키로 결제 조회에 성공한다")
    public void successFindPaymentWithPaymentKey() {
        //given
        given(paymentRepository.findByPgInfoPayKey(paymentKey)).willReturn(Optional.ofNullable(savedPayment));

        //when
        Payment findPaymentWithKey = paymentService.findByPgInfoPayKey(paymentKey);

        //then
        assertEquals(findPaymentWithKey.getPgInfo().getPayKey(), paymentKey);
    }

}
