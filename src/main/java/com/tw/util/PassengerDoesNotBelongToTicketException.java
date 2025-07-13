package com.tw.util;

public class PassengerDoesNotBelongToTicketException extends RuntimeException {
    public PassengerDoesNotBelongToTicketException() {}
    public PassengerDoesNotBelongToTicketException(String message) {
        super(message);
    }
}
