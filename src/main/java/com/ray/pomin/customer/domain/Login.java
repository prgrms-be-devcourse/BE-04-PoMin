package com.ray.pomin.customer.domain;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.ray.pomin.global.util.Validator.Condition.regex;
import static com.ray.pomin.global.util.Validator.validate;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = PROTECTED)
public class Login {

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9]+@[a-z]+(\\.[a-z]+)*(\\.[a-z]{2,})$";

    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@$!])(?=\\S+$).{8,}$";

    private String email;

    private String password;

    public Login(String email, String password, PasswordEncoder passwordEncoder) {
        validate(regex(EMAIL_PATTERN, email), "이메일 패턴이 일치하지 않습니다");
        validate(regex(PASSWORD_PATTERN, password), "비밀번호 패턴이 일치하지 않습니다");

        this.email = email;
        this.password = passwordEncoder.encode(password);
    }

}
