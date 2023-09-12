package com.ray.pomin.global.auth.token;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties("jwt")
public record JwtProperties(String secretKey, Map<String, TokenInfo> info) {

    public long getExpiration(String tokenName) {
        return info.get(tokenName).expiration;
    }

    public String getHeaderName(String tokenName) {
        return info.get(tokenName).header;
    }

    private record TokenInfo(int expiration, String header) {

        public TokenInfo(int expiration, String header) {
            this.expiration = expiration * 1000;
            this.header = header;
        }

    }
}
