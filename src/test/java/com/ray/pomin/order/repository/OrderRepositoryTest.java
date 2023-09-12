package com.ray.pomin.order.repository;

import com.ray.pomin.order.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    public void setUp() {
        Order order1 = Order.builder()
                .customerId(1L)
                .build();

        Order order2 = Order.builder()
                .customerId(2L)
                .build();

        entityManager.persist(order1);
        entityManager.persist(order2);
        entityManager.flush();
    }

    @Test
    public void successFindByCustomerId() {
        // Given
        Long customerId = 1L;

        // When
        List<Order> orders = orderRepository.findByCustomerId(customerId);

        // Then
        assertThat(orders).isNotEmpty();
    }

    @Test
    public void failFindByCustomerId() {
        // Given
        Long customerId = 3L; // 특정 customerId

        // When
        List<Order> orders = orderRepository.findByCustomerId(customerId);

        // Then
        assertThat(orders).isEmpty();
    }

}
