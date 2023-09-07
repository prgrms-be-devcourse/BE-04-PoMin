package com.ray.pomin.menu.domain;

import com.ray.pomin.common.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "option_group")
@NoArgsConstructor(access = PROTECTED)
public class OptionGroup extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "option_group_id")
    private Long id;

    private String name;

    @Column(name = "default_selection")
    private boolean defaultSelection;

    @Column(name = "exclusive_selection")
    private boolean exclusiveSelection;

    private int maxSize;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

}
