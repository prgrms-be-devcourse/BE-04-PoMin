package com.ray.pomin.store.controller.converter;

import com.ray.pomin.common.domain.Point;
import com.ray.pomin.store.controller.dto.AddressPoint;
import com.ray.pomin.store.controller.dto.AddressPoint.Documents;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.lang.Double.parseDouble;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MapPointConverterTest {

    @InjectMocks
    private MapPointConverter converter;

    @Mock
    private MapPointRestOperation operation;

    @Test
    @DisplayName("입력받은 주소를 위경도 값으로 변환에 성공한다")
    void successConvertAddressToPoint() {
        // given
        String address = "서울시 강남구 역삼동";
        String longitude = "123.1233";
        String latitude = "34.233";
        Documents documents = new Documents(null, null, null, longitude, latitude, address);
        AddressPoint point = new AddressPoint(List.of(documents), null);

        given(operation.getResponse(address)).willReturn(point);

        // when
        Point addressPoint = converter.getPoint(address);

        // then
        assertThat(addressPoint.getLatitude()).isEqualTo(parseDouble(latitude));
        assertThat(addressPoint.getLongitude()).isEqualTo(parseDouble(longitude));
    }

    @Test
    @DisplayName("입력받은 주소로 검색이 실패하여 위경도 값으로 변환에 실패한다")
    void failConvertAddressToPointWithNoPlace() {
        // given
        String address = "서울시 강남구 역삼동";

        given(operation.getResponse(address)).willThrow(IllegalArgumentException.class);

        // when & then
        assertThatThrownBy(() -> converter.getPoint(address))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
