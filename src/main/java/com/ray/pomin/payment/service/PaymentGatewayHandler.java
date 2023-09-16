package com.ray.pomin.payment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ray.pomin.payment.controller.dto.PaymentCancelRequest;
import com.ray.pomin.payment.controller.dto.PaymentInfo;
import com.ray.pomin.payment.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.text.MessageFormat.format;
import static java.util.Base64.getEncoder;
import static org.springframework.http.HttpMethod.POST;

@Component
@RequiredArgsConstructor
public class PaymentGatewayHandler {

    @Value("${toss.api.testSecretApiKey}")
    private String secretKey;

    private final RestTemplate restTemplate;

    private final PaymentGatewayResponseParser parser;

    PaymentInfo makePaymentRequest(String orderId, String paymentKey, int amount) throws JsonProcessingException {
        Map<String, String> paymentRequestBody = createPaymentRequest(orderId, paymentKey, amount);
        HttpHeaders headers = createRequestHeader();
        HttpEntity<Object> requestBody = new HttpEntity<>(paymentRequestBody, headers);

        String url = "https://api.tosspayments.com/v1/payments/confirm";
        ResponseEntity<String> response = restTemplate.exchange(url, POST, requestBody, String.class);

        return parser.getPaymentInfoFrom(response);
    }

    private Map<String, String> createPaymentRequest(String orderId, String paymentKey, int amount) {
        return Map.of("paymentKey", paymentKey,
                "orderId", orderId,
                "amount", String.valueOf(amount));
    }

    private HttpHeaders createRequestHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", getAuthorizations());
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }

    private String getAuthorizations() {
        byte[] encodedSecretKey = getEncoder().encode((secretKey + ":").getBytes(UTF_8));

        return "Basic "+ new String(encodedSecretKey, 0, encodedSecretKey.length);
    }

    public PaymentInfo cancelPaymentRequest(Payment payment) throws JsonProcessingException {
        String url = format("https://api.tosspayments.com/v1/payments/{0}/cancel", payment.getPgInfo().getPayKey());
        HttpEntity<PaymentCancelRequest> requestBody = new HttpEntity<>(new PaymentCancelRequest("결제취소사유"), createRequestHeader());

        ResponseEntity<String> response = restTemplate.exchange(url, POST, requestBody, String.class);

        return parser.getPaymentInfoFrom(response);
    }

}
