package com.ray.pomin.menu.service;

import com.ray.pomin.menu.domain.Menu;
import com.ray.pomin.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    public void save(Menu menu) {
        menuRepository.save(menu);
    }

    public Menu getOne(Long menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("찾고자하는 메뉴가 없습니다"));
    }

    public List<Menu> getAllInStore(Long storeId) {
        return menuRepository.findAllByStoreId(storeId);
    }

}
