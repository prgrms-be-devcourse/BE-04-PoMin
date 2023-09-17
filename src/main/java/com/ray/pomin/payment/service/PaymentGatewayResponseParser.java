package com.ray.pomin.payment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.pomin.payment.controller.dto.PaymentInfo;
import com.ray.pomin.payment.domain.PayMethod;
import com.ray.pomin.payment.domain.PayType;
import com.ray.pomin.payment.domain.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.ray.pomin.payment.domain.PayMethod.EASYPAY;
import static com.ray.pomin.payment.domain.PayMethod.findByTitle;

@Component
@RequiredArgsConstructor
public class PaymentGatewayResponseParser {

    private final ObjectMapper objectMapper;

    public PaymentInfo getPaymentInfoFrom(ResponseEntity<String> response) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        int amount = extractIntValue(jsonNode,"totalAmount");
        PaymentStatus status = extractValue(jsonNode,"status").equals("DONE") ? PaymentStatus.COMPLETE : PaymentStatus.CANCELED;
        String paymentKey = extractValue(jsonNode, "paymentKey");
        PayMethod payMethod = findByTitle(extractValue(jsonNode,"method"));
        PayType payType = getPayType(jsonNode, payMethod);
        LocalDateTime approvedAt = getTime(jsonNode);

        return new PaymentInfo(amount, status, paymentKey, payMethod, payType, approvedAt);
    }

    private int extractIntValue(JsonNode jsonNode, String valueName) {
        return jsonNode.get(valueName).intValue();
    }

    private String extractValue(JsonNode jsonNode, String valueName) {
        return jsonNode.get(valueName).textValue();
    }

    private LocalDateTime extractTimeValue(JsonNode jsonNode, String valueName) {
        String time = jsonNode.get(valueName).textValue().split("\\+")[0];
        return LocalDateTime.parse(time);
    }
    private PayType getPayType(JsonNode jsonNode, PayMethod payMethod) {
        if (payMethod == EASYPAY) {
            return PayType.findByTitle(extractValue(jsonNode,"easyPay"));
        }
        return PayType.findByCode(extractValue(jsonNode.get("card"), "issuerCode"));
    }

    private LocalDateTime getTime(JsonNode jsonNode) {
        if (jsonNode.get("status").textValue().equals("CANCELED")) {
            return extractTimeValue(jsonNode.get("cancels").get(0), "canceledAt");
        }
        return extractTimeValue(jsonNode, "approvedAt");
    }

}
