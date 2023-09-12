package com.ray.pomin.global.auth.model;

import java.util.Date;

public record Claims(Long id, String role, Date expiration) {

    public static Claims customer(Long id) {
        return new Claims(id, "ROLE_CUSTOMER", new Date());
    }

}
