package com.ray.pomin.common.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrderNumberGeneratorTest {

    public static final int RANDOM_DIGITS = 3;

    @Test
    public void testGenerateOrderNumber() {
        String orderNumber = OrderNumberGenerator.generateOrderNumber();
        assertNotNull(orderNumber);
        assertEquals(RANDOM_DIGITS + 13, orderNumber.length());
        assertTrue(orderNumber.matches("\\d+"));
    }

}
