package com.ray.pomin.global.auth.filter;

import com.ray.pomin.global.auth.model.Claims;
import com.ray.pomin.global.auth.model.JwtUser;
import com.ray.pomin.global.auth.token.JwtProperties;
import com.ray.pomin.global.auth.token.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = extractAccessToken(request);

        if (!accessToken.isBlank()) {
            saveAuthentication(accessToken);
        }

        filterChain.doFilter(request, response);
    }

    private String extractAccessToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(jwtProperties.getHeaderName("access"));

        if (authorizationHeader == null) {
            return "";
        }

        return authorizationHeader.replace("Bearer ", "");
    }

    private void saveAuthentication(String accessToken) {
        Claims claims = jwtService.extractClaim(accessToken);

        SecurityContextHolder.getContext().setAuthentication(createAuthentication(claims));
    }

    private UsernamePasswordAuthenticationToken createAuthentication(Claims claims) {
        return new UsernamePasswordAuthenticationToken(new JwtUser(claims.id()), null, List.of(claims::role));
    }

}
