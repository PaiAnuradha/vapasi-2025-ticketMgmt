package com.tw.exception;

public class PassengerNotLinkedToTicketException extends RuntimeException {
    public PassengerNotLinkedToTicketException() {
        super("Passenger Not Linked to Ticket");
    }
    public PassengerNotLinkedToTicketException(String message) {
        super(message);
    }
}
