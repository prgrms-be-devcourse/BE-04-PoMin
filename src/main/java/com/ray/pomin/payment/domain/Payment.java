package com.ray.pomin.payment.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.ray.pomin.global.util.Validator.validate;
import static com.ray.pomin.payment.domain.PaymentStatus.CANCELED;
import static jakarta.persistence.EnumType.STRING;
import static java.util.Objects.isNull;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
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

  @Builder
  private Payment(Long id, int amount, PaymentStatus status, PGInfo pgInfo, PayInfo payInfo, LocalDateTime approvedAt) {
    validatePayment(amount, status, pgInfo, payInfo, approvedAt);

    this.id = id;
    this.amount = amount;
    this.status = status;
    this.pgInfo = pgInfo;
    this.payInfo = payInfo;
    this.approvedAt = approvedAt;
  }

  private void validatePayment(int amount, PaymentStatus status, PGInfo pgInfo, PayInfo payInfo, LocalDateTime approvedAt) {
    validate(amount > 0, "결제금액은 0 보다 커야합니다");
    validate(!isNull(status), "결제상태는 필수 값입니다");
    validate(!isNull(pgInfo), "PG사 정보는 필수 값입니다");
    validate(!isNull(payInfo), "결제 수단 정보는 필수 값입니다");
    validate(!isNull(approvedAt), "결제 일시는 필수 값입니다");
    validate(approvedAt.isBefore(LocalDateTime.now()), "결제일시가 유효하지 않습니다");
  }

  public Payment cancel(LocalDateTime canceledAt) {
    return Payment.builder()
                    .id(id)
                    .amount(amount)
                    .status(CANCELED)
                    .pgInfo(new PGInfo(pgInfo.getProvider(), pgInfo.getPayKey()))
                    .payInfo(new PayInfo(payInfo.getMethod(), payInfo.getType()))
                    .approvedAt(canceledAt)
                    .build();
  }

}
