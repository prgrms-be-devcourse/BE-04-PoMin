package com.ray.pomin.review.domain;

import com.ray.pomin.global.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import static jakarta.persistence.FetchType.LAZY;

@Entity
public class ReviewImage extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String url;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "REVIEW_ID")
    private Review review;

}
