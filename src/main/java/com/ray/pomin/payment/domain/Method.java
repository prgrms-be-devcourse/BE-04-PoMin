package com.ray.pomin.payment.domain;

import java.util.Set;

public interface Method {

  String NOT_SUPPORTED = "지원하지 않는 결제 수단입니다.";
  Set<String> getOptions();
}
