package com.ray.pomin.payment.service;

import com.ray.pomin.payment.controller.dto.PaymentCancelRequest;
import com.ray.pomin.payment.controller.dto.PaymentRequestInfo;
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

    Payment makePaymentRequest(PaymentRequestInfo paymentRequestInfo) {
        Map<String, String> paymentRequestBody = createPaymentRequest(paymentRequestInfo.orderId(), paymentRequestInfo.paymentKey(), paymentRequestInfo.amount());
        HttpHeaders headers = createRequestHeader();
        HttpEntity<Object> requestBody = new HttpEntity<>(paymentRequestBody, headers);

        String url = "https://api.tosspayments.com/v1/payments/confirm";
        ResponseEntity<String> response = restTemplate.exchange(url, POST, requestBody, String.class);

        return parser.getPayment(response, paymentRequestInfo.customerId());
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

        return "Basic " + new String(encodedSecretKey, 0, encodedSecretKey.length);
    }

    public Payment cancelPaymentRequest(Payment payment) {
        String url = format("https://api.tosspayments.com/v1/payments/{0}/cancel", payment.getPgInfo().getPayKey());
        HttpEntity<PaymentCancelRequest> requestBody = new HttpEntity<>(new PaymentCancelRequest("결제취소사유"), createRequestHeader());
        ResponseEntity<String> response = restTemplate.exchange(url, POST, requestBody, String.class);
        Payment canceledPayment = parser.getPayment(response, payment.getCustomerId());

        return payment.cancel(canceledPayment.getApprovedAt());
    }

}
