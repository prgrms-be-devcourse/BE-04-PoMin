package com.ray.pomin.payment.domain;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import java.util.Set;

import static lombok.AccessLevel.*;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class GatewayInfo {

  private static final Set<String> options = Set.of("TOSS", "NICE", "NHN", "NONE");

  private String name; // PG사 이름

  private String payKey; // 거래 key

}
