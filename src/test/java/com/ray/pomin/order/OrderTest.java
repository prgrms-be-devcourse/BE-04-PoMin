package com.ray.pomin.order;

import com.ray.pomin.menu.domain.AdditionalDetails;
import com.ray.pomin.menu.domain.Menu;
import com.ray.pomin.menu.domain.MenuInfo;
import com.ray.pomin.menu.domain.MenuOption;
import com.ray.pomin.menu.domain.PrimaryDetails;
import com.ray.pomin.payment.domain.PGInfo;
import com.ray.pomin.payment.domain.PayInfo;
import com.ray.pomin.payment.domain.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static com.ray.pomin.payment.domain.PGType.TOSS;
import static com.ray.pomin.payment.domain.PayMethod.CARD;
import static com.ray.pomin.payment.domain.PayType.KB;
import static com.ray.pomin.payment.domain.PaymentStatus.COMPLETE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderTest {

    private Order order;

    private Menu menu1;

    private Menu menu2;

    @BeforeEach
    public void setUp() {
        order = new Order();

        PrimaryDetails primaryDetails1 = new PrimaryDetails("menu 1", "1000");
        PrimaryDetails primaryDetails2 = new PrimaryDetails("menu 2", "2000");

        AdditionalDetails additionalDetails = new AdditionalDetails("image", MenuOption.BEST_SELLER, "description");

        MenuInfo menuInfo1 = new MenuInfo(primaryDetails1, additionalDetails);
        menu1 = new Menu(menuInfo1, false, 1L);
        MenuInfo menuInfo2 = new MenuInfo(primaryDetails2, additionalDetails);
        menu2 = new Menu(menuInfo2, false, 1L);
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

    @Test
    public void testAcceptOrder() {
        OrderInfo newOrderInfo = new OrderInfo();

        Order order = Order.builder()
                .orderInfo(new OrderInfo())
                .orderStatus(OrderStatus.PAID)
                .build();

        Order acceptedOrder = order.acceptOrder(newOrderInfo);

        assertEquals(newOrderInfo, acceptedOrder.getOrderInfo());
        assertEquals(OrderStatus.COMPLETED, acceptedOrder.getOrderStatus());
    }

    @Test
    public void testGetTotalPrice() {
        OrderItem item1 = new OrderItem(menu1, 2);
        OrderItem item2 = new OrderItem(menu2, 3);

        Order order = Order.builder()
                .orderStatus(OrderStatus.CREATED)
                .orderItems(Arrays.asList(item1, item2))
                .build();

        int totalPrice = order.getTotalPrice();

        assertEquals(2 * 1000 + 3 * 2000, totalPrice);
    }

}
