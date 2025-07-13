package com.tw.util;

public class MaxPassengersExceededException extends RuntimeException {
    public MaxPassengersExceededException() {
        super("Max Passengers Exceeded");
    }

    public MaxPassengersExceededException(String message) {
        super(message);
    }
}
