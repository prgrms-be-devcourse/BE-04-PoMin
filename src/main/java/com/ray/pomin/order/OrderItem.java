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

    @OneToOne
    @JoinColumn(name = "MENU_ID")
    private Menu menu;

    private String name;

    private int price;

    @Getter
    private int count;

    public OrderItem(Menu menu, int count) {
        this.menu = menu;
        this.count = count;
        this.price = menu.getMenuInfo().getPrice() * count;
    }

    public int getItemPrice() {
        return menu.getPrice() * count;
    }

}
