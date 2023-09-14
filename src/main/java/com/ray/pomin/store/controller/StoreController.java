package com.ray.pomin.store.controller;

import com.ray.pomin.common.domain.Point;
import com.ray.pomin.store.controller.converter.MapPointConverter;
import com.ray.pomin.store.controller.dto.StoreSaveRequest;
import com.ray.pomin.store.domain.Store;
import com.ray.pomin.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    private final MapPointConverter mapPointConverter;

    @PostMapping("/stores")
    public ResponseEntity<Void> save(@RequestBody StoreSaveRequest request) {
        Point point = mapPointConverter.getPoint(request.mainAddress());
        Store store = request.toEntity(point);

        storeService.save(store);
        return ResponseEntity.noContent().build();
    }

}
