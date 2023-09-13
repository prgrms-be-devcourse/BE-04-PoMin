package com.ray.pomin.payment.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static com.ray.pomin.global.util.Validator.Condition.*;
import static com.ray.pomin.global.util.Validator.validate;
import static jakarta.persistence.EnumType.*;
import static java.util.Objects.*;
import static lombok.AccessLevel.*;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = PROTECTED)
public class PGInfo {

  @Enumerated(STRING)
  @Column(name = "PGType")
  private PGType provider;

  private String payKey;

  public PGInfo(PGType provider, String payKey) {
    validate(!isNull(provider), "PG사는 필수 값입니다");
    validate(hasContent(payKey), "결제키는 필수 값입니다");

    this.provider = provider;
    this.payKey = payKey;
  }

}
