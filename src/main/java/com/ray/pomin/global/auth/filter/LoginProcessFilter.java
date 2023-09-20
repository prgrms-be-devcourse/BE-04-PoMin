package com.ray.pomin.global.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.StringUtils;

import java.io.IOException;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class LoginProcessFilter extends AbstractAuthenticationProcessingFilter {

    private static final String DEFAULT_FILTER_PROCESSES_URL = "/login";

    private final ObjectMapper objectMapper;

    public LoginProcessFilter(ObjectMapper objectMapper, AuthenticationManager authenticationManager) {
        super(DEFAULT_FILTER_PROCESSES_URL, authenticationManager);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        validateRequest(request);

        Login login = objectMapper.readValue(request.getInputStream(), Login.class);
        login.valid();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login.email, login.password);

        return this.getAuthenticationManager().authenticate(token);
    }

    private static void validateRequest(HttpServletRequest request) {
        if (!request.getContentType().equals(APPLICATION_JSON_VALUE) || !request.getMethod().equals(POST.name())) {
            throw new IllegalArgumentException("처리 불가능한 요청입니다");
        }
    }

    record Login(String email, String password) {

        public void valid() {
            if (!StringUtils.hasText(email) || !StringUtils.hasText(password)) {
                throw new IllegalArgumentException("이메일와 비밀번호를 모두 입력해 주세요");
            }
        }

    }

}
