package com.ray.pomin.store.repository;

import com.ray.pomin.store.domain.Store;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

    @EntityGraph(attributePaths = {"storeImages"})
    Optional<Store> findById(Long id);

}
