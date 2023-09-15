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

    public List<Menu> getAllInStore(Long storeId) {
        return menuRepository.findAllByStoreId(storeId);
    }

}
