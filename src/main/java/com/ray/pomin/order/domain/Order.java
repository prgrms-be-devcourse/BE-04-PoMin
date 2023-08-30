package com.ray.pomin.order.domain;

import com.ray.pomin.global.domain.BaseEntity;
import com.ray.pomin.store.domain.Store;
import com.ray.pomin.user.domain.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import static jakarta.persistence.FetchType.LAZY;

@Entity
public class Order extends BaseEntity {

    @Id
    @Column(name = "ORDER_ID")
    @GeneratedValue
    private Long id;

    private String orderCode;

    private OrderStatus status;

    private int totalPrice;

    private Payment payment;

    private String requestContent;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private Customer user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "STORE_ID")
    private Store store;

}
