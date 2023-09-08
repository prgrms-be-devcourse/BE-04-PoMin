package com.ray.pomin.order;

import com.ray.pomin.common.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderLineItem extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "order_line_item_id")
    private Long id;

    private String name;

    private int count;

}