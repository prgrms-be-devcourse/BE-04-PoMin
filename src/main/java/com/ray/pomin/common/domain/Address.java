package com.ray.pomin.common.domain;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = PROTECTED)
public class Address {

    private String primaryAddress;

    private String additionalAddress;

    public Address(String primaryAddress, String additionalAddress) {
        this.primaryAddress = primaryAddress;
        this.additionalAddress = additionalAddress;
    }

}
