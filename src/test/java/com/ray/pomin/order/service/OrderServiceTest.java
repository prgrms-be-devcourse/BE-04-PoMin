package com.ray.pomin.order.service;

import com.ray.pomin.order.domain.Order;
import com.ray.pomin.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindOrderSuccess() {
        // given
        Long orderId = 1L;
        Order order = new Order();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // when
        Order result = orderService.findOrder(orderId);

        // then
        assertEquals(order, result);
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    public void testFindOrderNotFound() {
        // given
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(IllegalArgumentException.class, () -> orderService.findOrder(orderId));
    }

    @Test
    public void testCreateOrder() {
        // given
        Order order = new Order();
        when(orderRepository.save(order)).thenReturn(order);

        // when
        Order result = orderService.createOrder(order);

        // then
        assertEquals(order, result);
        verify(orderRepository, times(1)).save(order);
    }
}