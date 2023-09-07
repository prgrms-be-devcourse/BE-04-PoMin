package com.ray.pomin.payment.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

import static com.ray.pomin.global.util.Validator.validate;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Easypay implements Method {

  private String detail;

  @Transient
  private static final Set<String> options = Set.of("카카오페이","네이버베이","토스페이");

  public Easypay(String detail) {
    validate(options.contains(detail), NOT_SUPPORTED);
    this.detail = detail;
  }

  @Override
  public Set<String> getOptions() {
    return options;
  }

}
