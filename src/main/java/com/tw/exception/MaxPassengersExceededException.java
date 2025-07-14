package com.tw.exception;

public class MaxPassengersExceededException extends RuntimeException {
    public MaxPassengersExceededException() {
        super("Max Passengers Exceeded");
    }

    public MaxPassengersExceededException(String message) {
        super(message);
    }
}
