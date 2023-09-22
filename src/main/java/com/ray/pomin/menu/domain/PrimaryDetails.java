package com.ray.pomin.menu.domain;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = PROTECTED)
public class PrimaryDetails {

    private String name;

    private String price;

    public PrimaryDetails(String name, String price) {
        this.name = name;
        this.price = price;
    }

}
