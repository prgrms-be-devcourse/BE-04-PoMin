package com.ray.pomin.store.domain;

import com.ray.pomin.common.domain.Address;
import com.ray.pomin.common.domain.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StoreTest {

    @Test
    @DisplayName("store 생성에 성공한다")
    void successCreateStore() {
        // given
        String name = "store1";
        String phoneNumber = "010-1234-5678";
        Address address = new Address("서울시 강남구 역삼동", "12-2");
        Point point = new Point(34.123, 123.45);
        StoreTime storeTime = new StoreTime(false, LocalTime.of(12, 0), LocalTime.of(22, 0));
        List<String> images = List.of("imageUrl1", "imageUrl2");

        // when
        Store store = new Store(name, phoneNumber, address, point, storeTime, images);

        // then
        assertThat(store).isNotNull();
    }

    @ParameterizedTest(name = "[{index}] name : {1}, phoneNumber : {2}, address : {3}, point : {4}, storeTime : {5}, images : {6}")
    @MethodSource("storeBadSaveParameter")
    @DisplayName("storeImage 생성에 실패한다")
    void failCreateStore(String name, String phoneNumber, Address address, Point point, StoreTime storeTime, List<String> images) {
        // when & then
        assertThatThrownBy(() -> new Store(name, phoneNumber, address, point, storeTime, images))
                .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> storeBadSaveParameter() {
        return Stream.of(
                Arguments.arguments(null, "010-1234-5678", new Address("서울시 강남구 역삼동", "12-2"),
                        new Point(34.123, 123.45),
                        new StoreTime(false, LocalTime.of(12, 0), LocalTime.of(22, 0)),
                        List.of("imageUrl1", "imageUrl2")),
                Arguments.arguments("store1", null, new Address("서울시 강남구 역삼동", "12-2"),
                        new Point(34.123, 123.45),
                        new StoreTime(false, LocalTime.of(12, 0), LocalTime.of(22, 0)),
                        List.of("imageUrl1", "imageUrl2")),
                Arguments.arguments("store1", "010-1234-5678", null,
                        new Point(34.123, 123.45),
                        new StoreTime(false, LocalTime.of(12, 0), LocalTime.of(22, 0)),
                        List.of("imageUrl1", "imageUrl2")),
                Arguments.arguments("store1", "010-1234-5678", new Address("서울시 강남구 역삼동", "12-2"),
                        null, new StoreTime(false, LocalTime.of(12, 0), LocalTime.of(22, 0)),
                        List.of("imageUrl1", "imageUrl2")),
                Arguments.arguments("store1", "010-1234-5678", new Address("서울시 강남구 역삼동", "12-2"),
                        new Point(34.123, 123.45), null, List.of("imageUrl1", "imageUrl2")),
                Arguments.arguments("store1", "010-1234-5678", new Address("서울시 강남구 역삼동", "12-2"),
                        new Point(34.123, 123.45),
                        new StoreTime(false, LocalTime.of(12, 0), LocalTime.of(22, 0)), null)
        );
    }

}
