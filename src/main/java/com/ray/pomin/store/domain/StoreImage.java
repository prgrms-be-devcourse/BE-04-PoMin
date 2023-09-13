package com.ray.pomin.store.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

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
        this.image = image;
        this.store = store;
    }

}
