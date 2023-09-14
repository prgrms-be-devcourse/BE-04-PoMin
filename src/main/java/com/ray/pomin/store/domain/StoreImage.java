package com.ray.pomin.store.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.ray.pomin.global.util.Validator.validate;
import static java.util.Objects.isNull;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreImage {

    @Id
    @GeneratedValue
    private Long id;

    private String image;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    public StoreImage(String image, Store store) {
        validate(!isNull(image), "사진은 빈 값일 수 없습니다");
        validate(!isNull(store), "연결될 가게는 빈 값일 수 없습니다");

        this.image = image;
        this.store = store;
    }

}
