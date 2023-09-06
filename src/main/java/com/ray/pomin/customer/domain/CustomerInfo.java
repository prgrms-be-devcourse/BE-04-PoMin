package com.ray.pomin.customer.domain;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = PROTECTED)
public class CustomerInfo {

    private String name;

    private LocalDate birthDate;

    private String phoneNumber;

    public CustomerInfo(String name, LocalDate birthDate, String phoneNumber) {
        this.name = name;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
    }

}
