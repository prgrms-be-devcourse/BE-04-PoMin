package com.ray.pomin.order;

import com.ray.pomin.common.domain.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class OrderOption extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int price;

    @ManyToOne
    private OrderOptionGroup orderOptionGroup;

}
