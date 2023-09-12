package com.ray.pomin.common.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrderNumberGeneratorTest {

    public static final int RANDOM_DIGITS = 3;

    public static final String ORDER_NUMBER_REGEX = "\\d+";
    public static final int ORDER_NUMBER_LENGTH = 13;

    @Test
    public void testGenerateOrderNumber() {
        String orderNumber = OrderNumberGenerator.generateOrderNumber();

        assertNotNull(orderNumber);
        assertEquals(RANDOM_DIGITS + ORDER_NUMBER_LENGTH, orderNumber.length());
        assertTrue(orderNumber.matches(ORDER_NUMBER_REGEX));
    }

}
