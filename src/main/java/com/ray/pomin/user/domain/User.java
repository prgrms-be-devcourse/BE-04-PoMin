package com.ray.pomin.user.domain;

import com.ray.pomin.global.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue
    protected Long id;

    private String email;

    private String password;

    private String username;

    private String nickname;

    private LocalDate birthDate;

    private String phoneNumber;

    private String oauthProvider;

    private String address;

    private UserRole role;

}
