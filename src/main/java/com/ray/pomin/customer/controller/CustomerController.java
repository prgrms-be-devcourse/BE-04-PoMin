package com.ray.pomin.customer.controller;

import com.ray.pomin.customer.controller.dto.SaveCustomerRequest;
import com.ray.pomin.customer.controller.dto.SaveOAuthCustomerRequest;
import com.ray.pomin.customer.domain.Customer;
import com.ray.pomin.customer.service.CustomerService;
import com.ray.pomin.global.auth.model.Claims;
import com.ray.pomin.global.auth.model.OAuthCustomerInfo;
import com.ray.pomin.global.auth.model.Token;
import com.ray.pomin.global.auth.token.JwtService;
import com.ray.pomin.mail.service.MailService;
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

    private final JwtService jwtService;

    private final MailService mailService;

    private final CustomerService customerService;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/customers")
    public Token saveCustomer(@RequestBody SaveCustomerRequest request) {
        mailService.checkAuthentication(request.email());

        return getToken(request);
    }

    private Token getToken(SaveCustomerRequest request) {
        Customer savedCustomer = customerService.save(request.toEntity(passwordEncoder));

        return jwtService.createToken(Claims.customer(savedCustomer.getId()));
    }

    @PostMapping("/customers/oauth")
    public Token saveOAuthCustomer(@RequestBody SaveOAuthCustomerRequest request) {
        OAuthCustomerInfo customerInfo = customerService.additionalCustomerInfo(request.oAuthId());

        return getToken(request.convertToSaveCustomerRequest(customerInfo));
    }

}
