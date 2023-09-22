package com.ray.pomin.store.repository;

import com.ray.pomin.store.domain.Store;
import com.ray.pomin.store.service.dto.StoreSearchCondition;

import java.util.List;

public interface StoreListRepository {

    List<Store> findAllStores(StoreSearchCondition condition);

}
