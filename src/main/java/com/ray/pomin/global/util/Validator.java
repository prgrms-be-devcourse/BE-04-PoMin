package com.ray.pomin.global.util;

import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class Validator {

    public static void validate(boolean predicate, String failureMessage) {
        if (!predicate) {
            throw new IllegalArgumentException(failureMessage);
        }
    }

    @NoArgsConstructor(access = PRIVATE)
    public static class Condition {

        public static boolean hasContent(String target) {
            return StringUtils.hasText(target);
        }

        public static boolean regex(String pattern, String target) {
            return hasContent(pattern) && hasContent(target) && Pattern.matches(pattern, target);
        }
    }

}
