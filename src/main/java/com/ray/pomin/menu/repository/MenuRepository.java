package com.ray.pomin.menu.repository;

import com.ray.pomin.menu.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findAllByStoreId(Long storeId);

}
