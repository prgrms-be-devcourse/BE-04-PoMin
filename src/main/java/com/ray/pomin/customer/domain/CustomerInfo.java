package com.ray.pomin.customer.domain;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static com.ray.pomin.global.util.Validator.Condition.hasContent;
import static com.ray.pomin.global.util.Validator.validate;
import static java.util.Objects.isNull;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = PROTECTED)
public class CustomerInfo {

    private String name;

    private LocalDate birthDate;

    private PhoneNumber phoneNumber;

    public CustomerInfo(String name, LocalDate birthDate, PhoneNumber phoneNumber) {
        validate(hasContent(name), "이름은 필수값 입니다");
        validate(!isNull(birthDate), "생일정보는 필수 값 입니다");
        validate(!isNull(phoneNumber), "핸드폰 번호는 필수 값 입니다");

        this.name = name;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
    }

    public String getNumber() {
        return phoneNumber.getNumber();
    }

}
