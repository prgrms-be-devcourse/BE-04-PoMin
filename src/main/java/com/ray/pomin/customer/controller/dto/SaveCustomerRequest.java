package com.ray.pomin.customer.controller.dto;

import com.ray.pomin.customer.domain.Customer;
import com.ray.pomin.customer.domain.CustomerInfo;
import com.ray.pomin.customer.domain.Login;
import com.ray.pomin.customer.domain.PhoneNumber;
import com.ray.pomin.customer.domain.Provider;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

public record SaveCustomerRequest(
        String email, String password, String name,
        String nickname, String birthDate, String phoneNumber, String provider
) {

    public Customer toEntity(PasswordEncoder encoder) {
        return new Customer(
                new Login(email, password, encoder), nickname,
                new CustomerInfo(name, LocalDate.parse(birthDate), new PhoneNumber(phoneNumber)),
                Provider.valueOf(provider.toUpperCase())
        );
    }

}
