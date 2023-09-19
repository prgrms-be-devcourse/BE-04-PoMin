package com.ray.pomin.payment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;

import static com.ray.pomin.payment.domain.PGType.TOSS;
import static com.ray.pomin.payment.domain.PayMethod.CARD;
import static com.ray.pomin.payment.domain.PayType.KAKAO_PAY;
import static com.ray.pomin.payment.domain.PayType.KB;
import static com.ray.pomin.payment.domain.PaymentStatus.CANCELED;
import static com.ray.pomin.payment.domain.PaymentStatus.COMPLETE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentTest {

    @Test
    @DisplayName("결제 생성에 성공한다")
    void successCreatePayment() {
        // given
        Payment payment = Payment.builder()
                            .amount(3000)
                            .status(COMPLETE)
                            .payInfo(new PayInfo(CARD, KB))
                            .pgInfo(new PGInfo(TOSS, "payKey-randomValue3wd"))
                            .approvedAt(LocalDateTime.now())
                            .build();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -100})
    @DisplayName("총액이 음수 또는 0인 결제 생성에 실패한다")
    void failCreatePaymentWithInvalidAmount(int amount) {
        //when, then
        assertThatThrownBy(() -> Payment.builder()
                .amount(amount)
                .status(COMPLETE)
                .payInfo(new PayInfo(CARD, KB))
                .pgInfo(new PGInfo(TOSS, "payKey-randomValue3wd"))
                .approvedAt(LocalDateTime.now())
                .build())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("결제 수단과 결제 타입이 서로 매칭되지 않으면 결제 생성에 실패한다")
    public void failCreatePaymentWithInvalidPayUnit() {
        //when, then
        assertThatThrownBy(() -> Payment.builder()
                .amount(100)
                .status(COMPLETE)
                .payInfo(new PayInfo(CARD, KAKAO_PAY))
                .pgInfo(new PGInfo(TOSS, "payKey-randomValue3wd"))
                .approvedAt(LocalDateTime.now())
                .build())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("승인시점이 미래인 결제는 생성할 수 없다")
    void failCreatePaymentWithInvalidTime() {
        // when, then
        assertThatThrownBy(() -> Payment.builder()
                .amount(1000)
                .status(COMPLETE)
                .payInfo(new PayInfo(CARD, KB))
                .pgInfo(new PGInfo(TOSS, "payKey-randomValue3wd"))
                .approvedAt(LocalDateTime.now().plusHours(3))
                .build())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("결제를 취소할 수 있다")
    void successCancelPayment() {
        // given
        Payment payment = Payment.builder()
                .amount(1000)
                .status(COMPLETE)
                .payInfo(new PayInfo(CARD, KB))
                .pgInfo(new PGInfo(TOSS, "payKey-randomValue3wd"))
                .approvedAt(LocalDateTime.now().minusHours(3))
                .build();

        // when
        LocalDateTime canceledAt = LocalDateTime.now();
        Payment canceledPayment = payment.cancel(canceledAt);

        // then
        assertEquals(canceledPayment.getStatus(), CANCELED);
        assertEquals(canceledPayment.getApprovedAt(), canceledAt);
    }

}
