package com.ray.pomin.order;

import com.ray.pomin.common.util.OrderNumberGenerator;
import com.ray.pomin.customer.controller.dto.OrderRequest;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Embeddable
@AllArgsConstructor
@Getter
public class OrderInfo {

    private String orderNumber;

    private String registrationNumber;

    private String request;

    private int cookingMinute;

    public OrderInfo() {
        this.orderNumber = OrderNumberGenerator.generateOrderNumber();
    }

    public static OrderInfo acceptOrderInfo(String orderNumber, OrderRequest orderRequest) {
        return new OrderInfo(orderNumber, orderRequest.receiptNumber(), orderRequest.request(), orderRequest.cookingMinute());
    }

}
