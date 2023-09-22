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
public class AdditionalDetails {

    private String image;

    private MenuOption labels;

    private String description;

    public AdditionalDetails(String image, MenuOption labels, String description) {
        this.image = image;
        this.labels = labels;
        this.description = description;
    }

}
