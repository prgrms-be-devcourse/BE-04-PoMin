package com.ray.pomin.order.controller.dto;

public record OrderRequest(String receiptNumber, String request, int cookingMinute, long storeId) {
}

