package com.ray.pomin.customer.service;

import com.ray.pomin.customer.domain.Customer;
import com.ray.pomin.customer.repository.CustomerRepository;
import com.ray.pomin.global.auth.model.OAuthCustomerInfo;
import com.ray.pomin.global.auth.model.OAuthCustomerInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final OAuthCustomerInfoRepository oAuthUserInfoRepository;

    public Customer save(Customer customer) {
        customerRepository.findByLoginEmail(customer.getEmail())
                .ifPresent(findCustomer -> {
                    throw new IllegalArgumentException("사용 불가능 한 이메일입니다");
                });

        return customerRepository.save(customer);
    }

    public OAuthCustomerInfo additionalCustomerInfo(Long oAuthId) {
        return oAuthUserInfoRepository.findById(oAuthId)
                .orElseThrow(() -> new IllegalArgumentException("소셜 로그인을 먼저 진행해야 합니다"));
    }

}
