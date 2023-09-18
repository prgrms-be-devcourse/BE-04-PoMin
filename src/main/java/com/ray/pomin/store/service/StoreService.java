package com.ray.pomin.store.service;

import com.ray.pomin.store.domain.Store;
import com.ray.pomin.store.repository.StoreRepository;
import com.ray.pomin.store.service.dto.StoreSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public void save(Store store) {
        storeRepository.save(store);
    }

    public Store getOne(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 가게입니다"));
    }

    public List<Store> findAll(double latitude, double longitude) {
        return storeRepository.findAllStores(new StoreSearchCondition(latitude, longitude));
    }

}
