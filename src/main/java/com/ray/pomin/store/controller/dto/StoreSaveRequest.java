package com.ray.pomin.store.controller.dto;

import com.ray.pomin.common.domain.Address;
import com.ray.pomin.common.domain.Point;
import com.ray.pomin.store.domain.Store;
import com.ray.pomin.store.domain.StoreTime;

import java.time.LocalTime;
import java.util.List;

public record StoreSaveRequest(String name, String phoneNumber, String mainAddress,
                               String detailAddress, String openTime, String closeTime, List<String> images) {

    public Store toEntity(Point addressPoint) {
        Address address = new Address(mainAddress, detailAddress);
        StoreTime time = new StoreTime(false, LocalTime.parse(openTime), LocalTime.parse(closeTime));

        return new Store(name, phoneNumber, address, addressPoint, time, images);
    }

}
