package com.ray.pomin.menu.domain;

import com.ray.pomin.common.domain.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class Option extends BaseTimeEntity {

    @Id
    private Long id;

    private String name;

    private int price;

    @ManyToOne
    @JoinColumn(name = "OPTION_GROUP_ID")
    private OptionGroup optionGroup;

    public Option(Long id, String name, int price, OptionGroup optionGroup) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.optionGroup = optionGroup;
    }

}
