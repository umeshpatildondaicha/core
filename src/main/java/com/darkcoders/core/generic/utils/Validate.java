package com.darkcoders.core.generic.utils;

public final class Validate {
    private Validate() {}

    public static void checkNoneNull(Object... objects) {
        for (Object obj : objects) {
            if (obj == null) {
                throw new IllegalArgumentException("Object cannot be null");
            }
        }
    }

    public static void checkNoneEmpty(String... strings) {
        for (String str : strings) {
            if (str == null || str.trim().isEmpty()) {
                throw new IllegalArgumentException("String cannot be null or empty");
            }
        }
    }
} 