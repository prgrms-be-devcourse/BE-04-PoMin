package com.ray.pomin.store.service;

import com.ray.pomin.store.domain.Store;
import com.ray.pomin.store.domain.StoreStatus;
import com.ray.pomin.store.repository.StoreRepository;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

    private final StoreRepository repository;

    public StoreService(StoreRepository repository) {
        this.repository = repository;
    }

    public void modifyStatus(Long storeId, StoreStatus status) {

        Store store = repository.findById(storeId).orElseThrow(
                () -> new IllegalArgumentException("잘못된 가게 정보 입니다")
        );

        if (!store.getStatus().equals(status)) {
            store.setStatus(status);
            repository.save(store);
        }
    }

}
