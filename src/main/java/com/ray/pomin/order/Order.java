package com.ray.pomin.order;

import com.ray.pomin.common.domain.BaseTimeEntity;
import com.ray.pomin.payment.domain.Payment;
import com.ray.pomin.store.domain.Store;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "ORDERS")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseTimeEntity {

    @Getter
    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @Embedded
    private OrderInfo orderInfo;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ORDER_ID")
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "STORE_ID")
    private Store store;


    @Column(name = "CUSTOMER_ID")
    private Long customerId;

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

        if (store != null && !store.getTime().isOpen()) {
            throw new IllegalStateException("가게가 현재 열려 있지 않습니다.");
        }
    }

    public Order ordered() {
        return Order.builder()
                .id(this.id)
                .orderInfo(this.orderInfo)
                .orderItems(this.orderItems)
                .store(this.store)
                .customerId(this.customerId)
                .payment(this.payment)
                .orderStatus(OrderStatus.CREATED)
                .build();
    }

    public Order paid(Payment payment) {
        return Order.builder()
                .id(this.id)
                .orderInfo(this.orderInfo)
                .orderItems(this.orderItems)
                .store(this.store)
                .customerId(this.customerId)
                .payment(payment)
                .orderStatus(OrderStatus.PAID)
                .build();
    }

    public int getTotalPrice() {
        return orderItems.stream()
                .mapToInt(OrderItem::getItemPrice)
                .sum();
    }

    public Order acceptOrder(OrderInfo orderInfo) {
        return Order.builder()
                .id(this.id)
                .orderInfo(orderInfo)
                .orderItems(this.orderItems)
                .store(this.store)
                .customerId(this.customerId)
                .payment(payment)
                .orderStatus(OrderStatus.COMPLETED)
                .build();
    }

}
