package com.ray.pomin.order;

import com.ray.pomin.common.domain.BaseTimeEntity;
import com.ray.pomin.payment.domain.Payment;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @Embedded
    private OrderInfo orderInfo;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_ID")
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column(name = "STORE_ID")
    private Long storeId;

    @OneToOne
    @JoinColumn(name = "PAYMENT_ID")
    private Payment payment;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private OrderStatus orderStatus;

    public void place() {
        validateOrder();
        ordered();
    }

    private void validateOrder() {
        if (orderItems.isEmpty()) {
            throw new IllegalArgumentException("주문 항목이 비어 있습니다");
        }
    }

    private void ordered() {
        this.orderStatus = OrderStatus.CREATED;
    }

    public void payed() {
        this.orderStatus = OrderStatus.PAID;
    }

}
