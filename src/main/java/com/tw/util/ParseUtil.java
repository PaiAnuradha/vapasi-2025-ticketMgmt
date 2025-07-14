package com.tw.util;

public class ParseUtil {
    public static Gender parseGender(String input) {
        try {
            return Gender.valueOf(input.trim().toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid gender: " + input);
        }
    }

}
