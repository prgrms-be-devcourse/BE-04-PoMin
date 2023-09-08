package com.ray.pomin.payment.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.*;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class PGInfo {

  @Enumerated(EnumType.STRING)
  private PG company; // pg 사

  private String payKey; // 거래 key

}
