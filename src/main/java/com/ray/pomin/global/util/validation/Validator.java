package com.ray.pomin.global.util.validation;

import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class Validator {

    public static void validate(boolean failCondition, String failureMessage) {
        if (failCondition) {
            throw new IllegalArgumentException(failureMessage);
        }
    }

    @NoArgsConstructor(access = PRIVATE)
    public static class Condition {

        public static boolean regex(String regex, String target) {
            return isNull(target) || !Pattern.matches(regex, target);
        }

    }

}
