package com.ray.pomin.store.controller;

import com.ray.pomin.common.domain.Point;
import com.ray.pomin.menu.domain.Menu;
import com.ray.pomin.menu.service.MenuService;
import com.ray.pomin.store.controller.converter.MapPointConverter;
import com.ray.pomin.store.controller.dto.StoreInfo;
import com.ray.pomin.store.controller.dto.StoreSaveRequest;
import com.ray.pomin.store.controller.dto.StoreSimpleInfo;
import com.ray.pomin.store.domain.Store;
import com.ray.pomin.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    private final MenuService menuService;

    private final MapPointConverter mapPointConverter;

    @PostMapping("/stores")
    public ResponseEntity<Void> save(@RequestBody StoreSaveRequest request) {
        Point point = mapPointConverter.getPoint(request.mainAddress());
        Store store = request.toEntity(point);

        storeService.save(store);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stores/{storeId}")
    public StoreInfo getStoreInfo(@PathVariable Long storeId) {
        Store store = storeService.getOne(storeId);
        List<Menu> menus = menuService.getAllInStore(storeId);

        return new StoreInfo(store, menus);
    }

    @GetMapping("/stores")
    public List<StoreSimpleInfo> getAllStores(@RequestParam double latitude, @RequestParam double longitude) {
        return storeService.findAll(latitude, longitude).stream()
                .map(store -> new StoreSimpleInfo(store.getName(), store.getStoreImages()))
                .toList();
    }

}
