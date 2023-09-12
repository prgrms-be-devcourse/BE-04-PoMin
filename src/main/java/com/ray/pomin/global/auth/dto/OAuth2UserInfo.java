package com.ray.pomin.global.auth.dto;

import java.util.Map;

public record OAuth2UserInfo(Map<String, Object> attributes, String name, String email) {
}
