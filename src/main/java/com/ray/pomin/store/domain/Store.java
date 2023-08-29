package com.ray.pomin.store.domain;

import com.ray.pomin.global.domain.Address;
import com.ray.pomin.global.domain.BaseEntity;
import com.ray.pomin.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
public class Store extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Address address;

    private String phoneNumber;

    private String information;

    private String description;

    private LocalDateTime openTime;

    private LocalDateTime closeTime;

    private String imageUrl;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

}
