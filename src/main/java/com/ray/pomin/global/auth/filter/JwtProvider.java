package com.ray.pomin.global.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.pomin.global.auth.model.SecurityCustomer;
import com.ray.pomin.global.auth.model.Token;
import com.ray.pomin.global.auth.token.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

import static com.ray.pomin.global.auth.model.Claims.customer;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
public class JwtProvider implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SecurityCustomer principal = (SecurityCustomer) authentication.getPrincipal();
        Token token = jwtService.createToken(customer(principal.getId()));

        response.setCharacterEncoding("UTF-8");
        response.setContentType(APPLICATION_JSON_VALUE);
        response.getWriter().print(objectMapper.writeValueAsString(token));
    }

}
