package com.ray.pomin.common.util;

import java.util.Random;

public class OrderNumberGenerator {

    private static final int RANDOM_DIGITS = 3;

    public static String generateOrderNumber() {
        long currentTimeMillis = System.currentTimeMillis();
        String randomDigits = generateRandomDigits();
        return randomDigits + currentTimeMillis;
    }

    private static String generateRandomDigits() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < RANDOM_DIGITS; i++) {
            int digit = random.nextInt(10);
            stringBuilder.append(digit);
        }

        return stringBuilder.toString();
    }

}
