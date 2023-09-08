package com.ray.pomin.order;

import com.ray.pomin.common.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "order_option_group")
@NoArgsConstructor(access = PROTECTED)
public class OrderOptionGroup extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "order_option_group_id")
    private Long id;

    private String name;

    @ManyToOne
    private OrderLineItem orderLineItem;

}
