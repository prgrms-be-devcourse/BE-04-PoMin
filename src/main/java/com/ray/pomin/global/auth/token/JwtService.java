package com.ray.pomin.global.auth.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.ray.pomin.global.auth.model.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String TOKEN_NAME = "access";

    private final JwtProperties properties;

    public String createToken(Claims claims) {
        return JWT.create()
                .withSubject(TOKEN_NAME)
                .withExpiresAt(new Date(claims.expiration().getTime() + properties.getExpiration(TOKEN_NAME)))
                .withClaim("id", claims.id())
                .withClaim("role", claims.role())
                .sign(Algorithm.HMAC512(properties.secretKey()));
    }

    public Claims extractClaim(String accessToken) {
        Map<String, Claim> claims = JWT.decode(accessToken).getClaims();

        Long id = claims.get("id").asLong();
        String role = claims.get("role").asString();
        Date expiration = claims.get("exp").asDate();

        return new Claims(id, role, expiration);
    }

}
