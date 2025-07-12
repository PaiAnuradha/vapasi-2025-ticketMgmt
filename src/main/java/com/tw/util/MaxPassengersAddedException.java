package com.tw.util;

public class MaxPassengersAddedException extends RuntimeException {
    public MaxPassengersAddedException() {
    }

    public MaxPassengersAddedException(String message) {
        super(message);
    }
}
