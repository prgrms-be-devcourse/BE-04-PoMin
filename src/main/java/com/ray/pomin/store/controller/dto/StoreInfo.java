package com.ray.pomin.store.controller.dto;

import com.ray.pomin.menu.domain.Menu;
import com.ray.pomin.store.domain.Store;

import java.util.List;

public record StoreInfo(String storeName, String phoneNumber, String address,
                        List<String> images, List<MenuInfo> menuInfos) {

    public StoreInfo(Store store, List<Menu> menus) {
        this(
                store.getName(),
                store.getPhoneNumber(),
                store.fullAddress(),
                store.getStoreImages(),
                menus.stream()
                        .map(MenuInfo::new)
                        .toList()
        );
    }


    public record MenuInfo(String menuName, String price, String description,
                           String imageUrl, String label, boolean verifyAge) {

        public MenuInfo(Menu menu) {
            this(
                    menu.getMenuInfo().getPrimaryDetails().getName(),
                    menu.getMenuInfo().getPrimaryDetails().getPrice(),
                    menu.getMenuInfo().getAdditionalDetails().getDescription(),
                    menu.getMenuInfo().getAdditionalDetails().getImage(),
                    menu.getMenuInfo().getAdditionalDetails().getLabels().name(),
                    menu.isVerifyAge()
            );
        }

    }

}
