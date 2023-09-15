package com.ray.pomin.order;

import com.ray.pomin.common.domain.BaseTimeEntity;
import com.ray.pomin.menu.domain.Menu;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @Getter
    @OneToOne
    @JoinColumn(name = "MENU_ID")
    private Menu menu;

    private String name;

    @Getter
    private int count;

}
