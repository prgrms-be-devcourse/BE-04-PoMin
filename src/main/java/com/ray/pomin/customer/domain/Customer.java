package com.ray.pomin.customer.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = PROTECTED)
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private Login login;

    private String nickname;

    @Embedded
    private CustomerInfo information;

    public Customer(Login login, String nickname, CustomerInfo information) {
        this.login = login;
        this.nickname = nickname;
        this.information = information;
    }

}
