package com.ray.pomin.order.controller.dto;

import com.ray.pomin.order.OrderInfo;

public record OrderRequest(String receiptNumber, String requestedDetails, int cookingMinute, long storeId) {

    public OrderInfo createOrderInfo(String orderNuber) {
        return new OrderInfo(orderNuber, receiptNumber, requestedDetails, cookingMinute);
    }

}

