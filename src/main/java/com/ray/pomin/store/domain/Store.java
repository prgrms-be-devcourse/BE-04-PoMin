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

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
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

}
