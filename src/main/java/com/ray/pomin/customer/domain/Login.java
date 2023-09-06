package com.ray.pomin.customer.domain;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = PROTECTED)
public class Login {

    private String email;

    private String password;

    private String provider;

    public Login(String email, String password, String provider) {
        this.email = email;
        this.password = password;
        this.provider = provider;
    }

}
