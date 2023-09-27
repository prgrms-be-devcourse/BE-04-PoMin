package com.ray.pomin.menu.controller;

import com.ray.pomin.menu.controller.dto.SaveOptionRequest;
import com.ray.pomin.menu.domain.Menu;
import com.ray.pomin.menu.domain.OptionGroup;
import com.ray.pomin.menu.service.MenuService;
import com.ray.pomin.menu.service.OptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OptionController {

    private final OptionService optionService;
    private final MenuService menuService;

    @PostMapping("/api/v1/options")
    public void saveOption(@RequestBody SaveOptionRequest request) {
        Long menuId = request.menuId();
        Menu menu = menuService.getOne(menuId);

        OptionGroup optionGroup = request.toEntity(menu);

        optionService.save(optionGroup);
    }
}
