package com.ray.pomin.order.repository;

import com.ray.pomin.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerId(Long customerId);

    Optional<Order> findByOrderInfoOrderNumber(String orderNumber);

}
