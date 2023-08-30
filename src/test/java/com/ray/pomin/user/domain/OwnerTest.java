package com.ray.pomin.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OwnerTest {

    @Test
    @DisplayName("사장 생성에 성공한다")
    void successCreateOwnerDomain() {
        // given
        String loginId = "owner";
        String password = "owner1!";
        String email = "owner@gmail.com";

        // when
        Owner owner = new Owner(loginId, password, email);

        // then
        assertThat(owner).isNotNull();
    }

    @ParameterizedTest(name = "{index} loginId : {0} & password : {1} & email : {2}")
    @MethodSource("ownerConstructorArgument")
    @DisplayName("검증 로직 실패로 인해사장 생성에 실패한다")
    void failCreateOwnerWithFailValidation(String loginId, String password, String email) {
        // when & then
        assertThatThrownBy(() -> new Owner(loginId, password, email))
                .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> ownerConstructorArgument() {
        return Stream.of(
                Arguments.arguments(null, null, null),
                Arguments.arguments(null, null, "owner@gmail.com"),
                Arguments.arguments("owner", null, null),
                Arguments.arguments(null, "password", null),
                Arguments.arguments(null, "password", "owner@gmail.com"),
                Arguments.arguments("owner", null, "owner@gmail.com"),
                Arguments.arguments("owner", "password", null),
                Arguments.arguments("owner", "password", "failEmailValidation")
        );
    }
}
