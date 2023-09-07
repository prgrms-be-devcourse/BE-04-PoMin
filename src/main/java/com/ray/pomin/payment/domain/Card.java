package com.ray.pomin.payment.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

import static com.ray.pomin.global.util.Validator.validate;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Card implements Method {

  private String detail;

  @Transient
  private static final Set<String> options = Set.of(
                                          "국민","신한","비씨","삼성",
                                          "현대","농협","롯데","하나",
                                          "우리","씨티","광주은행","수협",
                                          "전북은행","우체국","제주","MG새마을",
                                          "신협","카카오뱅크","케이뱅크","저축은행","KDB산업"
                                      );


  public Card(String detail) {
    validate(options.contains(detail), NOT_SUPPORTED);
    this.detail = detail;
  }

  @Override
  public Set<String> getOptions() {
    return options;
  }
}
