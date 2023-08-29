package com.ray.pomin.option.domain;

import com.ray.pomin.global.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import static jakarta.persistence.FetchType.LAZY;

@Entity
public class SubOption extends BaseEntity {

    @Id
    @Column(name = "SUB_OOPTION_ID")
    @GeneratedValue
    private Long id;

    private String name;

    private int additionalPrice;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "OPTION_ID")
    private Option option;

}
