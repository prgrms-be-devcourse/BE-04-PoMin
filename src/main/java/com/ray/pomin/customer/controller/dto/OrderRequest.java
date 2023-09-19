package com.ray.pomin.customer.controller.dto;

public record OrderRequest(String receiptNumber, String request, int cookingMinute, long storeId) {
}

