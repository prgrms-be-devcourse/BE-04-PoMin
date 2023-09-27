package com.ray.pomin.menu.controller;

import com.ray.pomin.menu.controller.dto.SaveMenuRequest;
import com.ray.pomin.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/api/v1/menus")
    public ResponseEntity<Void> saveMenu(@RequestBody SaveMenuRequest request) {
        menuService.save(request.toEntity());

        return ResponseEntity.ok().build();
    }

}
