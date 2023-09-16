package com.ray.pomin.payment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.pomin.payment.controller.dto.PaymentInfo;
import com.ray.pomin.payment.domain.PayMethod;
import com.ray.pomin.payment.domain.PayType;
import com.ray.pomin.payment.domain.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.ray.pomin.payment.domain.PayMethod.EASYPAY;
import static com.ray.pomin.payment.domain.PayMethod.findByTitle;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentGatewayResponseParser {

    private final ObjectMapper objectMapper;

    public PaymentInfo getPaymentInfoFrom(ResponseEntity<String> response) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        int amount = jsonNode.get("totalAmount").intValue();
        PaymentStatus status = jsonNode.get("status").textValue().equals("DONE") ? PaymentStatus.COMPLETE : PaymentStatus.CANCELED;
        String paymentKey = jsonNode.get("paymentKey").textValue();
        PayMethod payMethod = findByTitle(jsonNode.get("method").textValue());
        PayType payType = getPayType(jsonNode, payMethod);
        LocalDateTime approvedAt = getApprovedAt(jsonNode);

        return new PaymentInfo(amount, status, paymentKey, payMethod, payType, approvedAt);
    }

    private LocalDateTime getApprovedAt(JsonNode jsonNode) {
        if (jsonNode.get("status").textValue().equals("CANCELED")) {
            String canceledAt = jsonNode.get("cancels").get(0).get("canceledAt").textValue().split("\\+")[0];
            return LocalDateTime.parse(canceledAt);
        }
        String approvedAt = jsonNode.get("approvedAt").textValue().split("\\+")[0];
        return LocalDateTime.parse(approvedAt);
    }

    private PayType getPayType(JsonNode jsonNode, PayMethod payMethod) {
        if (payMethod == EASYPAY) {
            return PayType.findByTitle(jsonNode.get("easyPay").get("provider").textValue());
        }
        return PayType.findByCode(jsonNode.get("card").get("issuerCode").textValue());
    }

}
