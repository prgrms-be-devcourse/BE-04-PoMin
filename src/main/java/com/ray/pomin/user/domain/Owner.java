package com.ray.pomin.user.domain;

import com.ray.pomin.global.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Owner extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String loginId;

    private String password;

    private String email;

}
