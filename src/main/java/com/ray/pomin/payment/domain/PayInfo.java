package com.ray.pomin.payment.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static com.ray.pomin.global.util.Validator.validate;
import static jakarta.persistence.EnumType.*;
import static lombok.AccessLevel.*;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = PROTECTED)
public class PayInfo {

  @Enumerated(STRING)
  private PayMethod method;

  @Enumerated(STRING)
  private PayType type;

  public PayInfo(PayMethod method, PayType type) {
    validate(!Objects.isNull(method), "결제 수단은 필수 값입니다");
    validate(!Objects.isNull(type), "결제 종류는 필수 값입니다");
    validate(method.hasPayType(type), "지원하지 않는 결제 종류입니다");

    this.method = method;
    this.type = type;
  }

  public PayMethod getMethod() {
    return method;
  }

  public PayType getType() {
    return type;
  }

}
