package com.ray.pomin.menu.controller.dto;

import com.ray.pomin.menu.domain.AdditionalDetails;
import com.ray.pomin.menu.domain.Menu;
import com.ray.pomin.menu.domain.MenuInfo;
import com.ray.pomin.menu.domain.MenuOption;
import com.ray.pomin.menu.domain.PrimaryDetails;

public record SaveMenuRequest(
        String name, String price,
        String imageUrl, String menuOptionName, String description,
        boolean verifyAge, Long storeId
) {

    public Menu toEntity() {
        PrimaryDetails primaryDetails = new PrimaryDetails(name, price);
        AdditionalDetails additionalDetails = new AdditionalDetails(imageUrl, MenuOption.valueOf(menuOptionName.toUpperCase()), description);
        MenuInfo info = new MenuInfo(primaryDetails, additionalDetails);

        return new Menu(info, verifyAge, storeId);
    }

}
