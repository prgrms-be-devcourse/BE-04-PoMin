package com.ray.pomin.customer.domain;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.ray.pomin.global.util.Validator.Condition.regex;
import static com.ray.pomin.global.util.Validator.validate;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = PROTECTED)
public class PhoneNumber {

    private static final String PHONE_NUMBER_PATTERN = "^010-\\d{4}-\\d{4}$";

    private String number;

    public PhoneNumber(String number) {
        validate(regex(PHONE_NUMBER_PATTERN, number), "핸드폰 번호 패턴이 일치하지 않습니다");
        this.number = number;
    }

}
