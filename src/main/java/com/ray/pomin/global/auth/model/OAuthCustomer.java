package com.ray.pomin.global.auth.model;

import com.ray.pomin.global.auth.dto.OAuth2UserInfo;
import com.ray.pomin.global.auth.dto.CustomerRegistration;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class OAuthCustomer implements OAuth2User {

    private final OAuth2UserInfo userInfo;
    private final CustomerRegistration registration;

    @Override
    public Map<String, Object> getAttributes() {
        return userInfo.attributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_CUSTOMER");
    }

    @Override
    public String getName() {
        return userInfo.name();
    }

    public CustomerRegistration getRegistration() {
        return this.registration;
    }

}
