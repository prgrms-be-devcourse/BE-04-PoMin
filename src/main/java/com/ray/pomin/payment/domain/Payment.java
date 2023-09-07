package com.ray.pomin.payment.domain;

import com.ray.pomin.common.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Payment extends BaseTimeEntity {

  @Id
  @GeneratedValue
  @Column(name = "PAYMENT_ID")
  private Long id; // 결제 ID

  private int amount; // 결제금액

  private PaymentStatus status; // 결제상태 : 완료, 취소

  @Embedded
  private GatewayInfo gatewayInfo; // PG사 관련 정보

  @Embedded
  private Method method; // 결제 수단 정보

}
