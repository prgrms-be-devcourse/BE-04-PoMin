package com.ray.pomin.global.auth;

import com.ray.pomin.global.auth.dto.OAuth2UserInfo;
import com.ray.pomin.global.auth.dto.OAuthAttributes;
import com.ray.pomin.global.auth.dto.CustomerRegistration;
import com.ray.pomin.global.auth.model.OAuthCustomer;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class OAuthCustomerService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo userInfo = OAuthAttributes.of(registrationId, super.loadUser(userRequest).getAttributes());

        String email = userInfo.email();
        String name = userInfo.name();
        CustomerRegistration registration = new CustomerRegistration(email, name, registrationId);

        return new OAuthCustomer(userInfo, registration);
    }

}
