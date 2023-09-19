package com.ray.pomin.customer.controller.dto;

public record OrderRequest(int receiptNumber, int cookingMinute, long storeId) {
}

