package com.ray.pomin.store.repository;

import com.ray.pomin.common.domain.Address;
import com.ray.pomin.common.domain.Point;
import com.ray.pomin.store.domain.Store;
import com.ray.pomin.store.domain.StoreTime;
import com.ray.pomin.store.service.dto.StoreSearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StoreConditionListRepositoryTest {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreListRepositoryImpl storeConditionListRepository;

    @Test
    void findAllStore() {
        // given
        Address address = new Address("강남구", "역삼동");
        StoreTime time = new StoreTime(false, LocalTime.of(12, 0), LocalTime.of(22, 0));
        List<String> images = List.of("image1");

        storeRepository.saveAll(List.of(
                new Store(1L, "store1", "010-1111-2222", address, new Point(37.6564929887299161, 127.06442614207607), time, images),
                new Store(2L, "store2", "010-1111-2222", address, new Point(37.654353781192924, 127.0542251979854), time, images),
                new Store(3L, "store3", "010-1111-2222", address, new Point(11222.2, 2.2), time, images),
                new Store(4L, "store4", "010-1111-2222", address, new Point(13.4, 14.4), time, images),
                new Store(5L, "store5", "010-1111-2222", address, new Point(13.5, 14.5), time, images),
                new Store(6L, "store6", "010-1111-2222", address, new Point(13.6, 14.6), time, images)
        ));

        // when
        List<Store> stores = storeConditionListRepository.findAllStores(new StoreSearchCondition(37.6531852873842, 127.04778768624769));

        // then
        System.out.println(stores);
        assertThat(stores).hasSize(2);
    }


}
