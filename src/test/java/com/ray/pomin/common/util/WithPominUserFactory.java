package com.ray.pomin.common.util;

import com.ray.pomin.global.auth.model.JwtUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class WithPominUserFactory implements WithSecurityContextFactory<WithPominUser> {

    @Override
    public SecurityContext createSecurityContext(WithPominUser annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(new JwtUser(Long.valueOf(annotation.id())), null, List.of(() -> "ROLE_USER")));

        return context;
    }

}
