package com.ray.pomin.customer.controller;

import com.ray.pomin.customer.controller.dto.SaveCustomerRequest;
import com.ray.pomin.customer.domain.Customer;
import com.ray.pomin.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/customers")
    public Token saveCustomer(@RequestBody SaveCustomerRequest request) {
        Customer savedCustomer = customerService.save(request.toEntity(passwordEncoder));

        return createJWT(savedCustomer);
    }

    private Token createJWT(Customer customer) {
        return new Token("accessToken");
    }

    public record Token(String accessToken) {
    }

}
