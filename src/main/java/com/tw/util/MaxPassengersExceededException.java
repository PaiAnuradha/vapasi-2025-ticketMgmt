package com.tw.util;

public class MaxPassengersExceededException extends RuntimeException {
    public MaxPassengersExceededException() {
    }

    public MaxPassengersExceededException(String message) {
        super(message);
    }
}
