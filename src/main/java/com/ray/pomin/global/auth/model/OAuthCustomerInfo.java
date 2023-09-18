package com.ray.pomin.global.auth.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("User")
public class OAuthCustomerInfo {

    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private String name;

    private String providerName;

    public OAuthCustomerInfo(String email, String name, String providerName) {
        this.email = email;
        this.name = name;
        this.providerName = providerName;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getProviderName() {
        return providerName;
    }

}
