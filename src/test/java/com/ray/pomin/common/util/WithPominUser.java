package com.ray.pomin.common.util;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@WithSecurityContext(factory = WithPominUserFactory.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface WithPominUser {

    String id() default "1";

}
