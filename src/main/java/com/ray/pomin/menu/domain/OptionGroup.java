package com.ray.pomin.menu.domain;

import com.ray.pomin.common.domain.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class OptionGroup extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Long price;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private Option option;

}
