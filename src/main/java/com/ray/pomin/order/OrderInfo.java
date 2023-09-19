package com.ray.pomin.order;

import com.ray.pomin.common.util.OrderNumberGenerator;
import com.ray.pomin.order.controller.dto.OrderRequest;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Embeddable
@AllArgsConstructor
public class OrderInfo {

    private String orderNumber;

    private String registrationNumber;

    private String request;

    private int cookingMinute;

    public OrderInfo() {
        this.orderNumber = OrderNumberGenerator.generateOrderNumber();
    }

}
