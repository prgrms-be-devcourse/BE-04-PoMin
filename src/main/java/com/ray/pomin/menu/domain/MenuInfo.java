package com.ray.pomin.menu.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = PROTECTED)
public class MenuInfo {

    @Embedded
    private PrimaryDetails primaryDetails;

    @Embedded
    private AdditionalDetails additionalDetails;

    public MenuInfo(PrimaryDetails primaryDetails, AdditionalDetails additionalDetails) {
        this.primaryDetails = primaryDetails;
        this.additionalDetails = additionalDetails;
    }

    public int getPrice() {
        return Integer.parseInt(primaryDetails.getPrice());
    }

}
