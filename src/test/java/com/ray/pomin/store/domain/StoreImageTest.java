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

class StoreImageTest {

    @Test
    @DisplayName("store 생성에 성공한다")
    void successCreateStoreImage() {
        // given
        String name = "store1";
        String phoneNumber = "010-1234-5678";
        Address address = new Address("서울시 강남구 역삼동", "12-2");
        Point point = new Point(34.123, 123.45);
        StoreTime storeTime = new StoreTime(false, LocalTime.of(12, 0), LocalTime.of(22, 0));
        List<String> images = List.of("imageUrl1", "imageUrl2");
        Store store = new Store(name, phoneNumber, address, point, storeTime, images);

        // when
        StoreImage image = new StoreImage("image", store);

        // then
        assertThat(image).isNotNull();
    }

    @ParameterizedTest(name = "[{index}] image : {1}, store : {2}")
    @MethodSource("storeImageBadSaveParameter")
    @DisplayName("store 생성에 실패한다")
    void failCreateStoreImage(String image, Store store) {
        // when & then
        assertThatThrownBy(() -> new StoreImage(image, store))
                .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> storeImageBadSaveParameter() {
        String name = "store1";
        String phoneNumber = "010-1234-5678";
        Address address = new Address("서울시 강남구 역삼동", "12-2");
        Point point = new Point(34.123, 123.45);
        StoreTime storeTime = new StoreTime(false, LocalTime.of(12, 0), LocalTime.of(22, 0));
        List<String> images = List.of("imageUrl1", "imageUrl2");
        Store store = new Store(name, phoneNumber, address, point, storeTime, images);

        return Stream.of(
                Arguments.arguments(null, store),
                Arguments.arguments("image", null)
        );
    }
    
}
