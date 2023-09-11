package com.ray.pomin.order.repository;

import com.ray.pomin.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
