package com.ray.pomin.payment.service;

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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.ray.pomin.payment.domain.PaymentStatus.COMPLETE;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Base64.getEncoder;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

  @Value("${toss.api.testSecretApiKey}")
  private String secretKey;

  private final String FINAL_TOSSPAYMENT_URL = "https://api.tosspayments.com/v1/payments/confirm";

  private final PaymentRepository paymentRepository;

  @Transactional
  public Long doFinalPaymentRequest(String orderId, String paymentKey, int amount) {

    ResponseEntity<Map> responseEntity = sendFinalPaymentRequest(orderId, paymentKey, amount);

    if (responseEntity.getStatusCode() != OK) {
      throw new IllegalArgumentException("결제에 실패했습니다.");
    }
    return paymentRepository.save(createPayment(responseEntity.getBody())).getId();
  }

  private Payment createPayment(Map paidRequestBody) {

    String paymentKey = paidRequestBody.get("paymentKey").toString();
    String method = paidRequestBody.get("method").toString();
    int amount = Integer.parseInt(paidRequestBody.get("totalAmount").toString());
    PayType payType;

    if (method.equals("간편결제")) {
      Map<String, Object> easyPay = (Map<String, Object>) paidRequestBody.get("easyPay");
      payType = PayType.findByTitle(easyPay.get("provider").toString());
    } else {
      Map<String, Object> card = (Map<String, Object>) paidRequestBody.get("card");
      payType = PayType.findByCode(card.get("issuerCode").toString());
    }

    return new Payment(amount, COMPLETE, new PGInfo(PGType.TOSS, paymentKey),
            new PayInfo(PayMethod.findByTitle(method), payType));
  }

  private ResponseEntity<Map> sendFinalPaymentRequest(String orderId, String paymentKey, int amount) {
    HashMap<String, String> paymentRequestInfo = new HashMap<>();
    paymentRequestInfo.put("paymentKey", paymentKey);
    paymentRequestInfo.put("orderId", orderId);
    paymentRequestInfo.put("amount", String.valueOf(amount));

    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", getAuthorizations());
    headers.set("Content-Type", "application/json");

    HttpEntity<Object> requestBody = new HttpEntity<>(paymentRequestInfo, headers);
    return restTemplate.postForEntity(FINAL_TOSSPAYMENT_URL, requestBody, Map.class);
  }

  private String getAuthorizations() {
    byte[] encodedSecretKey = getEncoder().encode((secretKey + ":").getBytes(UTF_8));
    return "Basic "+ new String(encodedSecretKey, 0, encodedSecretKey.length);
  }

}
