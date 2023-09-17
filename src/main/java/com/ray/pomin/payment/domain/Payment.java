package com.ray.pomin.payment.domain;

import com.ray.pomin.payment.controller.dto.PaymentInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.ray.pomin.global.util.Validator.validate;
import static jakarta.persistence.EnumType.STRING;
import static java.util.Objects.isNull;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = PROTECTED)
public class Payment {

  @Id
  @GeneratedValue
  @Column(name = "PAYMENT_ID")
  private Long id;

  private int amount;

  @Enumerated(STRING)
  private PaymentStatus status;

  @Embedded
  private PGInfo pgInfo;

  @Embedded
  private PayInfo payInfo;

  private LocalDateTime approvedAt;

  public Payment(int amount, PaymentStatus status, PGInfo pgInfo, PayInfo payInfo, LocalDateTime approvedAt) {
    validate(amount > 0, "결제금액은 0 보다 커야합니다");
    validate(!isNull(status), "결제상태는 필수 값입니다");
    validate(!isNull(pgInfo), "PG사 정보는 필수 값입니다");
    validate(!isNull(payInfo), "결제 수단 정보는 필수 값입니다");
    validate(!isNull(approvedAt), "결제 일시는 필수 값입니다");

    this.amount = amount;
    this.status = status;
    this.pgInfo = pgInfo;
    this.payInfo = payInfo;
    this.approvedAt = approvedAt;
  }

  public Payment cancel(PaymentInfo paymentInfo) {
    return Payment.builder()
                    .id(id)
                    .amount(amount)
                    .status(paymentInfo.status())
                    .pgInfo(new PGInfo(pgInfo.getProvider(), pgInfo.getPayKey()))
                    .payInfo(new PayInfo(payInfo.getMethod(), payInfo.getType()))
                    .approvedAt(paymentInfo.approvedAt())
                    .build();
  }

}
