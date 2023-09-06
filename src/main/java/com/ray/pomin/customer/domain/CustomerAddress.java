package com.ray.pomin.customer.domain;

import com.ray.pomin.common.domain.Address;
import com.ray.pomin.common.domain.Point;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = PROTECTED)
public class CustomerAddress {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private Address address;

    @Embedded
    private Point addressPoint;

    private boolean selected;

    private Long userId;

    public CustomerAddress(Address address, Point addressPoint, Long userId) {
        this.address = address;
        this.addressPoint = addressPoint;
        this.selected = true;
        this.userId = userId;
    }

}
