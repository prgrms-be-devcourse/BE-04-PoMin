package com.ray.pomin.option.domain;

import com.ray.pomin.global.domain.BaseEntity;
import com.ray.pomin.menu.domain.Menu;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import static jakarta.persistence.FetchType.LAZY;

@Entity
public class Option extends BaseEntity {

    @Id
    @Column(name = "OPTION_ID")
    @GeneratedValue
    private Long id;

    private String name;

    @Embedded
    private SelectedCount count;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MENU_ID")
    private Menu menu;

}
