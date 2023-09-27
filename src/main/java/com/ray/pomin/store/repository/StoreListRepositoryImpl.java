package com.ray.pomin.store.repository;

import com.ray.pomin.store.domain.Store;
import com.ray.pomin.store.service.dto.StoreSearchCondition;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.DoubleUnaryOperator;

@Repository
@RequiredArgsConstructor
public class StoreListRepositoryImpl implements StoreListRepository {

    private final EntityManager em;

    private static final double DISTANCE_KILOMETERS = 1.5;

    private static final double EARTH_RADIUS = 6371.0;

    private static final double LATITUDE_DIFFERENCE = (DISTANCE_KILOMETERS / EARTH_RADIUS * (180.0 / Math.PI));

    private static final DoubleUnaryOperator CALC_LONGITUDE_DIFFERENCE = currentLatitude -> DISTANCE_KILOMETERS / (EARTH_RADIUS * Math.cos(Math.toRadians(currentLatitude))) * (180.0 / Math.PI);

    @Override
    public List<Store> findAllStores(StoreSearchCondition condition) {
        RangePoint result = calculateCoordinateRange(condition.latitude(), condition.longitude());

        return em.createQuery("""
                        select s
                        from Store s
                        join fetch s.storeImages
                        where s.addressPoint.latitude between :minLatitude and :maxLatitude
                        and s.addressPoint.longitude between :minLongitude and :maxLongitude
                        """, Store.class)
                .setParameter("minLatitude", result.minLatitude())
                .setParameter("maxLatitude", result.maxLatitude())
                .setParameter("minLongitude", result.minLongitude())
                .setParameter("maxLongitude", result.maxLongitude())
                .getResultList();
    }

    public RangePoint calculateCoordinateRange(double latitude, double longitude) {
        double longitudeDifference = CALC_LONGITUDE_DIFFERENCE.applyAsDouble(latitude);

        return new RangePoint(latitude - LATITUDE_DIFFERENCE, latitude + LATITUDE_DIFFERENCE,
                longitude - longitudeDifference, longitude + longitudeDifference);
    }

    private record RangePoint(double minLatitude, double maxLatitude, double minLongitude, double maxLongitude) {

    }

}
