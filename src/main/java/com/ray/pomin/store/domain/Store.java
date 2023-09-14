package com.ray.pomin.store.domain;

import com.ray.pomin.common.domain.Address;
import com.ray.pomin.common.domain.BaseTimeEntity;
import com.ray.pomin.common.domain.Point;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.ray.pomin.global.util.Validator.validate;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static java.util.Objects.isNull;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = false)
public class Store extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String phoneNumber;

    @Embedded
    private Address address;

    @Embedded
    private Point addressPoint;

    @Embedded
    private StoreTime time;

    @OneToMany(mappedBy = "store", cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    private List<StoreImage> storeImages = new ArrayList<>();

    public Store(String name, String phoneNumber, Address address, Point addressPoint, StoreTime time, List<String> images) {
        validateStore(name, phoneNumber, address, addressPoint, time, images);

        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.addressPoint = addressPoint;
        this.time = time;
        this.storeImages.addAll(convertImage(images));
    }

    private void validateStore(String name, String phoneNumber, Address address, Point addressPoint, StoreTime time, List<String> images) {
        validate(!isNull(name), "이름은 빈값일 수 없습니다");
        validate(!isNull(phoneNumber), "가게 전화번호는 빈값일 수 없습니다");
        validate(!isNull(address), "주소는 빈값일 수 없습니다");
        validate(!isNull(addressPoint), "주소 좌표는 빈값일 수 없습니다");
        validate(!isNull(time), "가게 운영 시간은 빈값일 수 없습니다");
        validate(!isNull(images), "가게 사진은 빈값일 수 없습니다");
    }

    private List<StoreImage> convertImage(List<String> images) {
        return images.stream()
                .map(image -> new StoreImage(image, this))
                .toList();
    }

}
