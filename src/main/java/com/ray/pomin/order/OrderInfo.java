package com.ray.pomin.order;

import com.ray.pomin.common.util.OrderNumberGenerator;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class OrderInfo {

    private String orderNumber;

    private String registrationNumber;

    private String request;

    public OrderInfo() {
        this.orderNumber = OrderNumberGenerator.generateOrderNumber();
    }

}
