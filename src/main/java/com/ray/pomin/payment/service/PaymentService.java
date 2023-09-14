package com.ray.pomin.payment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.pomin.payment.controller.dto.PaymentCancelRequest;
import com.ray.pomin.payment.domain.PGInfo;
import com.ray.pomin.payment.domain.PGType;
import com.ray.pomin.payment.domain.PayInfo;
import com.ray.pomin.payment.domain.PayMethod;
import com.ray.pomin.payment.domain.PayType;
import com.ray.pomin.payment.domain.Payment;
import com.ray.pomin.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.Map;
import java.util.NoSuchElementException;

import static com.ray.pomin.payment.domain.PayMethod.EASYPAY;
import static com.ray.pomin.payment.domain.PayMethod.findByTitle;
import static com.ray.pomin.payment.domain.PaymentStatus.COMPLETE;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Base64.getEncoder;
import static org.springframework.http.HttpMethod.POST;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

  @Value("${toss.api.testSecretApiKey}")
  private String secretKey;

  private final ObjectMapper objectMapper;

  private final PaymentRepository paymentRepository;

  @Transactional
  public Long doFinalPaymentRequest(String orderId, String paymentKey, int amount) throws JsonProcessingException {
    ResponseEntity<String> responseEntity = sendFinalPaymentRequest(orderId, paymentKey, amount);

    return paymentRepository.save(createPayment(responseEntity.getBody())).getId();
  }

  private ResponseEntity<String> sendFinalPaymentRequest(String orderId, String paymentKey, int amount) {
    RestTemplate restTemplate = new RestTemplate();
    Map<String, String> paymentRequestBody = createPaymentRequest(orderId, paymentKey, amount);
    HttpHeaders headers = createRequestHeader();
    HttpEntity<Object> requestBody = new HttpEntity<>(paymentRequestBody, headers);

    final String url = "https://api.tosspayments.com/v1/payments/confirm";

    return restTemplate.postForEntity(url, requestBody, String.class);
  }

  private Map<String, String> createPaymentRequest(String orderId, String paymentKey, int amount) {
    return Map.of("paymentKey", paymentKey,
                  "orderId", orderId,
                  "amount", String.valueOf(amount));
  }

  private Payment createPayment(String paidRequestBody) throws JsonProcessingException {
    JsonNode jsonNode = objectMapper.readTree(paidRequestBody);
    PayMethod payMethod = findByTitle(jsonNode.get("method").textValue());
    String paymentKey = jsonNode.get("paymentKey").textValue();
    int amount = jsonNode.get("totalAmount").intValue();
    PayType payType;

    if (payMethod == EASYPAY) {
       payType = PayType.findByTitle(jsonNode.get("easyPay").get("provider").textValue());
    } else {
       payType = PayType.findByCode(jsonNode.get("card").get("issuerCode").textValue());
    }

    return new Payment(amount, COMPLETE, new PGInfo(PGType.TOSS, paymentKey),
            new PayInfo(payMethod, payType));
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

  @Transactional
  public void cancel(Payment payment) {
      sendPaymentCancelRequest(payment);
      Payment canceledPayment = payment.cancel();
      paymentRepository.save(canceledPayment);
  }

  private void sendPaymentCancelRequest(Payment payment) {
    RestTemplate restTemplate = new RestTemplate();
    String url = MessageFormat.format("https://api.tosspayments.com/v1/payments/{0}/cancel", payment.getPgInfo().getPayKey());
    HttpEntity<PaymentCancelRequest> requestBody = new HttpEntity<>(new PaymentCancelRequest("결제취소사유"), createRequestHeader());

    restTemplate.exchange(url, POST, requestBody, String.class);
  }

  public Payment findOne(Long paymentId) {
    return paymentRepository.findById(paymentId)
            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 결제건 입니다."));
  }

}
