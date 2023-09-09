package com.ray.pomin.payment.domain;

import com.ray.pomin.common.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static com.ray.pomin.global.util.Validator.validate;
import static java.util.Objects.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = PROTECTED)
public class Payment extends BaseTimeEntity {

  @Id
  @GeneratedValue
  @Column(name = "PAYMENT_ID")
  private Long id; // 결제 ID

  private int amount; // 결제금액

  private PaymentStatus status; // 결제상태 : 완료, 취소

  @Embedded
  private PGInfo pgInfo; // PG사 관련 정보

  @Embedded
  private PayInfo payInfo; // 결제 수단 상세 정보

  public Payment(int amount, PaymentStatus status, PGInfo pgInfo, PayInfo payInfo) {
    validate(amount > 0, "결제금액은 0 보다 커야합니다");
    validate(!isNull(status), "결제상태는 필수 값입니다");
    validate(!isNull(pgInfo), "PG사 정보는 필수 값입니다");
    validate(!isNull(payInfo), "결제 수단 정보는 필수 값입니다");
    this.amount = amount;
    this.status = status;
    this.pgInfo = pgInfo;
    this.payInfo = payInfo;
  }

}