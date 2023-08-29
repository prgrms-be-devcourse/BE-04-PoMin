package com.ray.pomin.menu.domain;

import com.ray.pomin.global.domain.BaseEntity;
import com.ray.pomin.store.domain.Store;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import static jakarta.persistence.FetchType.LAZY;

@Entity
public class Menu extends BaseEntity {

    @Id
    @Column(name = "MENU_ID")
    @GeneratedValue
    private Long id;

    @Embedded
    private MenuInfo info;

    private boolean onSale;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "STORE_ID")
    private Store store;

}
