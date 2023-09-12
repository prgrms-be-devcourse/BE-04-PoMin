package com.ray.pomin.payment.domain;

import java.util.Arrays;

public enum PayType {
  KB("국민", "11"),
  HANA("하나", "21"),
  NH("농협", "91"),

  KAKAO_PAY("카카오페이", "kakao"),
  NAVER_PAY("네이버페이", "naver"),
  TOSS_PAY("토스페이", "toss");

  private String title;
  private String code;

  PayType(String title, String code) {
    this.title = title;
    this.code = code;
  }

  public String getTitle() {
    return title;
  }
  public String getCode() {
    return code;
  }

  public static PayType findByCode(String code) {
    return Arrays.stream(PayType.values())
            .filter(payType -> payType.code.equals(code))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("해당 코드의 결제 타입은 지원하지 않습니다."));
  }

  public static PayType findByTitle(String title) {
    return Arrays.stream(PayType.values())
            .filter(payType -> payType.title.equals(title))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("해당 결제 타입은 지원하지 않습니다."));
  }
}
