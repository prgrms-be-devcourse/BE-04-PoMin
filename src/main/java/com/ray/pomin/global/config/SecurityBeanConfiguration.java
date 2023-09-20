package com.ray.pomin.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.pomin.global.auth.filter.JwtAuthorizationFilter;
import com.ray.pomin.global.auth.filter.JwtProvider;
import com.ray.pomin.global.auth.filter.LoginProcessFilter;
import com.ray.pomin.global.auth.token.JwtProperties;
import com.ray.pomin.global.auth.token.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SecurityBeanConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter(JwtService jwtService, JwtProperties properties) {
        return new JwtAuthorizationFilter(jwtService, properties);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtProvider jwtProviderHandler(JwtService jwtService, ObjectMapper objectMapper) {
        return new JwtProvider(jwtService, objectMapper);
    }

    @Bean
    public LoginProcessFilter loginProcessFilter(ObjectMapper objectMapper, AuthenticationManager authenticationManager, JwtProvider jwtProviderHandler) {
        LoginProcessFilter loginProcessFilter = new LoginProcessFilter(objectMapper, authenticationManager);
        loginProcessFilter.setAuthenticationSuccessHandler(jwtProviderHandler);

        return loginProcessFilter;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        return daoAuthenticationProvider;
    }

}
