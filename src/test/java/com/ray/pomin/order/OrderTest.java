package com.ray.pomin.order;

import com.ray.pomin.payment.domain.PGInfo;
import com.ray.pomin.payment.domain.PayInfo;
import com.ray.pomin.payment.domain.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.ray.pomin.payment.domain.PGType.TOSS;
import static com.ray.pomin.payment.domain.PayMethod.CARD;
import static com.ray.pomin.payment.domain.PayType.KB;
import static com.ray.pomin.payment.domain.PaymentStatus.COMPLETE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderTest {

    private Order order;

    @BeforeEach
    public void setUp() {
        order = new Order();
    }

    @Test
    public void testPaidOrder() {
        Order paidOrder = order.paid(Payment.builder()
                .amount(3000)
                .status(COMPLETE)
                .payInfo(new PayInfo(CARD, KB))
                .pgInfo(new PGInfo(TOSS, "payKey-randomValue3wd"))
                .approvedAt(LocalDateTime.now())
                .build());

        assertEquals(OrderStatus.PAID, paidOrder.getOrderStatus());
    }

    @Test
    public void failOrderValidation() {
        assertThrows(IllegalArgumentException.class, () -> {
            order.place();
        });
    }

    @Test
    public void successOrderValidation() {
        order.getOrderItems().add(new OrderItem());

        assertDoesNotThrow(() -> {
            order.place();
        });
    }

}
