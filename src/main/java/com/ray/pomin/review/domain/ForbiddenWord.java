package com.ray.pomin.review.domain;

import com.ray.pomin.global.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class ForbiddenWord extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String word;

}
