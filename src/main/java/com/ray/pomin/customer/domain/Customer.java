package com.ray.pomin.customer.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.ray.pomin.global.util.Validator.Condition.hasContent;
import static com.ray.pomin.global.util.Validator.validate;
import static jakarta.persistence.CascadeType.ALL;
import static java.util.Objects.isNull;
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

    @OneToMany(mappedBy = "customer", cascade = ALL, orphanRemoval = true)
    private List<LoginProvider> loginProviders = new ArrayList<>();

    public Customer(Login login, String nickname, CustomerInfo information, Provider provider) {
        validateCustomer(login, nickname, information, provider);

        this.login = login;
        this.nickname = nickname;
        this.information = information;
        this.loginProviders.add(new LoginProvider(provider, this));
    }

    private static void validateCustomer(Login login, String nickname, CustomerInfo information, Provider provider) {
        validate(!isNull(login), "로그인 정보는 필수 값입니다");
        validate(hasContent(nickname), "닉네임은 필수 값입니다");
        validate(!isNull(information), "사용자 정보는 필수 값입니다");
        validate(!isNull(provider), "로그인 제공자 정보는 필수 값입니다");
    }

    public Customer addLoginProvider(String providerName) {
        Customer customer = new Customer(this.login, this.nickname, this.information, Provider.valueOf(providerName));
        customer.loginProviders.addAll(this.loginProviders);

        return customer;
    }

    public Long getId() {
        return this.id;
    }

    public String getEmail() {
        return login.getEmail();
    }
}
