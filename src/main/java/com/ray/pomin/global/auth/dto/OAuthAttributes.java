package com.ray.pomin.global.auth.dto;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public enum OAuthAttributes {

    KAKAO("kakao", attributes -> {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        return new OAuth2UserInfo(
                attributes,
                profile.get("nickname").toString(),
                account.get("email").toString()
        );
    })
    ;

    private final String registrationId;
    private final Function<Map<String, Object>, OAuth2UserInfo> of;

    OAuthAttributes(String registrationId, Function<Map<String, Object>, OAuth2UserInfo> of) {
        this.registrationId = registrationId;
        this.of = of;
    }

    public static OAuth2UserInfo of(String providerId, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(provider -> provider.registrationId.equals(providerId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .of.apply(attributes);
    }

}
