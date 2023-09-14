package com.ray.pomin.global.config;

import com.ray.pomin.global.auth.filter.JwtAuthorizationFilter;
import com.ray.pomin.global.auth.token.JwtProperties;
import com.ray.pomin.global.auth.token.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SecurityBeanConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter(JwtService jwtService, JwtProperties properties) {
        return new JwtAuthorizationFilter(jwtService, properties);
    }

}
