package com.ray.pomin.store.domain;

import com.ray.pomin.global.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Category extends BaseEntity {

    @Id
    @Column(name = "CATEGORY_ID")
    @GeneratedValue
    private Long id;

    private String name;

    private String image;

}
