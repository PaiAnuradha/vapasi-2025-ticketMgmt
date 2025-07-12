package com.tw.util;

public class FieldCannotBeNull extends RuntimeException {
    public FieldCannotBeNull() {

    }
    public FieldCannotBeNull(String message) {
        super(message);
    }
}
