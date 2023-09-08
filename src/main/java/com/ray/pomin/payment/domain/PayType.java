package com.ray.pomin.payment.domain;

public enum PayType {
  KB("국민"),
  HANA("하나"),
  NH("농협"),

  KAKAO_PAY("카카오페이"),
  NAVER_PAY("네이버페이"),
  TOSS_PAY("토스페이");

  private String title;

  PayType(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

}
