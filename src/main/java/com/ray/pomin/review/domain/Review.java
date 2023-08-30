package com.ray.pomin.review.domain;

import com.ray.pomin.global.domain.BaseEntity;
import com.ray.pomin.order.domain.Order;
import com.ray.pomin.store.domain.Store;
import com.ray.pomin.user.domain.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import static jakarta.persistence.FetchType.LAZY;

@Entity
public class Review extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String content;

    @Embedded
    private Star star;

    @Enumerated
    private VisibleType visibleType;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "STORE_ID")
    private Store store;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

}
