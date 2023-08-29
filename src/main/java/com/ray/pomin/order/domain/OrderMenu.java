package com.ray.pomin.order.domain;

import com.ray.pomin.global.domain.BaseEntity;
import com.ray.pomin.menu.domain.Menu;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import static jakarta.persistence.FetchType.LAZY;

@Entity
public class OrderMenu extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private int count;

    private int totalPrice;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MENU_ID")
    private Menu menu;

}
