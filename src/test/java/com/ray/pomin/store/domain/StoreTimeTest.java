package com.ray.pomin.store.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StoreTimeTest {

    @Test
    @DisplayName("store 생성에 성공한다")
    void successCreateStoreImage() {
        // given & when
        StoreTime storeTime = new StoreTime(false, LocalTime.of(12, 0), LocalTime.of(22, 0));

        // then
        assertThat(storeTime).isNotNull();
    }

    @ParameterizedTest(name = "[{index}] isOpen : {1}, open : {2}, close : {3}")
    @MethodSource("storeTimeBadSaveParameter")
    @DisplayName("storeTime 생성에 실패한다")
    void failCreateStoreImage(boolean isOpen, LocalTime open, LocalTime close) {
        // when & then
        assertThatThrownBy(() -> new StoreTime(isOpen, open, close))
                .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> storeTimeBadSaveParameter() {
        return Stream.of(
                Arguments.arguments(false, null, LocalTime.of(22, 0)),
                Arguments.arguments(false, LocalTime.of(12, 0), null),
                Arguments.arguments(true, null, LocalTime.of(22, 0)),
                Arguments.arguments(true, LocalTime.of(12, 0), null),
                Arguments.arguments(true, LocalTime.of(22, 0), LocalTime.of(12, 0))
        );
    }

}
