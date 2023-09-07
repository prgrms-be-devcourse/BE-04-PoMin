package com.ray.pomin.menu.domain;

import com.ray.pomin.common.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class Option extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "option_id")
    private Long id;

    private String name;

    @Column(name = "default_selection")
    private boolean defaultSelection;

    @Column(name = "exclusice_selection")
    private boolean exclusiveSelection;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

}
