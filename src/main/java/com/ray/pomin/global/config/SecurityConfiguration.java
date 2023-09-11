package com.ray.pomin.global.config;

import com.ray.pomin.global.auth.OAuthCustomerService;
import com.ray.pomin.global.auth.OAuthSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@Import(SecurityBeanConfiguration.class)
public class SecurityConfiguration {

    private final OAuthCustomerService oAuthCustomerService;
    private final OAuthSuccessHandler oAuthSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .headers(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .rememberMe(AbstractHttpConfigurer::disable)
                .anonymous(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry ->
                        registry
                                .anyRequest().permitAll()
                )
                .oauth2Login(configure ->
                        configure.userInfoEndpoint(userConfig ->
                                userConfig.userService(oAuthCustomerService))
                                .successHandler(oAuthSuccessHandler)
                );

        return http.build();
    }
}
