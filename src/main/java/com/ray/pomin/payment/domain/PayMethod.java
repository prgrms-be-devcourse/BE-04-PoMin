package com.ray.pomin.payment.domain;

import java.util.Arrays;
import java.util.List;

import static com.ray.pomin.payment.domain.PayType.*;

public enum PayMethod {
  CARD("카드", List.of(KB, NH, HANA)),
  EASYPAY("간편결제", List.of(KAKAO_PAY, NAVER_PAY, TOSS_PAY));

  private String title;
  private List<PayType> payList;

  PayMethod(String title, List<PayType> payList) {
    this.title = title;
    this.payList = payList;
  }

  public boolean hasPayType(PayType payType) {
    return payList.stream()
            .anyMatch(pay -> pay == payType);
  }

  public String getTitle() {
    return title;
  }

}
