package com.ray.pomin.user.domain;

import com.ray.pomin.global.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.ray.pomin.global.util.validation.Validator.Condition.regex;
import static com.ray.pomin.global.util.validation.Validator.validate;
import static java.util.Objects.isNull;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class Owner extends BaseEntity {

    private static final String EMAIL_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final String PASSWORD_PATTERN = "^(?=.*[a-zA-Z\\d])(?=.*?[?!@$]).{8,}$";

    @Id
    @GeneratedValue
    private Long id;

    private String loginId;

    private String password;

    private String email;

    public Owner(String loginId, String password, String email, PasswordEncoder passwordEncoder) {
        validate(isNull(loginId), "로그인 아이디를 입력하세요");
        validate(regex(PASSWORD_PATTERN, password), "비밀번호를 입력하세요");
        validate(regex(EMAIL_PATTERN, email), "이메일 형식에 맞게 작성하세요");

        this.loginId = loginId;
        this.password = passwordEncoder.encode(password);
        this.email = email;
    }

    public String getLoginId() {
        return loginId;
    }

}
