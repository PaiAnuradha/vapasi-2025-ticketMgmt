package com.tw.exception;

public class PassengerAlreadyExistsException extends RuntimeException {
    PassengerAlreadyExistsException() {
        super("Passenger already exists");
    }
    public PassengerAlreadyExistsException(String aadhar) {
        super("Passenger with Aadhar "+ aadhar + " already exists");
    }
}
