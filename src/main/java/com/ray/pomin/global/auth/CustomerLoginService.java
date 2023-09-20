package com.ray.pomin.global.auth;

import com.ray.pomin.customer.repository.CustomerRepository;
import com.ray.pomin.global.auth.model.SecurityCustomer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerLoginService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new SecurityCustomer(customerRepository.findByLoginEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("가입된 고객이 없습니다")));
    }

}
