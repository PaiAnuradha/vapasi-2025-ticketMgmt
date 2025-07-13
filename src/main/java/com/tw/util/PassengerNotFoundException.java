package com.tw.util;

public class PassengerNotFoundException extends RuntimeException {
    public PassengerNotFoundException() {
        super("Passenger Not Found");
    }

    public PassengerNotFoundException(String message) {
        super(message);
    }

}
