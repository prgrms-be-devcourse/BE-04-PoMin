package com.ray.pomin.order;

import com.ray.pomin.common.domain.BaseTimeEntity;
import com.ray.pomin.common.util.OrderNumberGenerator;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderInfo extends BaseTimeEntity {

    private String orderNumber;

    private String registrationNumber;

    public OrderInfo(String orderNumber) {
        this.orderNumber = OrderNumberGenerator.generateOrderNumber();
    }

}
