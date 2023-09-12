package com.ray.pomin.global.auth.model;

import java.util.Date;

public record Claims(Long id, String role, Date expiration) {
}
