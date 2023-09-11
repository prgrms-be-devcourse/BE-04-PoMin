package com.ray.pomin.menu.domain;

import com.ray.pomin.common.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class Menu extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "MENU_ID")
    private Long id;

    private String name;

    private int price;

    private String description;

    @Column(name = "is_popular_badge")
    private boolean isPopularBadge;

}
