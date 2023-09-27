package com.ray.pomin.menu.domain;

import com.ray.pomin.common.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "option_group")
@NoArgsConstructor(access = PROTECTED)
public class OptionGroup extends BaseTimeEntity {

    @Id
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

    @OneToMany(mappedBy = "optionGroup", cascade = ALL, orphanRemoval = true)
    private List<Option> options = new ArrayList<>();

    public OptionGroup(Long id, String name, boolean defaultSelection, boolean exclusiveSelection, int maxSize, Menu menu) {
        this.id = id;
        this.name = name;
        this.defaultSelection = defaultSelection;
        this.exclusiveSelection = exclusiveSelection;
        this.maxSize = maxSize;
        this.menu = menu;
    }

    public void addOption(List<Option> options) {
        this.options.addAll(options);
    }

}
