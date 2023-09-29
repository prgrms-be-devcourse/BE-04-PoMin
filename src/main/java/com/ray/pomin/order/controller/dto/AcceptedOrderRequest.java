package com.ray.pomin.order.controller.dto;

import com.ray.pomin.customer.domain.Customer;
import com.ray.pomin.order.Order;
import com.ray.pomin.payment.controller.dto.PaymentCreateRequest;

import java.time.LocalDateTime;

public record AcceptedOrderRequest(
        Long id,
        String orderNumber,
        String requestedDetails,
        int totalPrice,
        String customerPhoneNumber,
        LocalDateTime orderedAt,
        Long storeId,
        PaymentCreateRequest paymentCreateRequest
) {

    public AcceptedOrderRequest(Order order, Customer customer) {
        this(
                order.getId(),
                order.getOrderNumber(),
                order.getRequest(),
                order.getTotalPrice(),
                customer.getPhoneNumber(),
                order.getPayment().getApprovedAt(),
                order.getId(),
                new PaymentCreateRequest(order.getPayment())
        );
    }

}
