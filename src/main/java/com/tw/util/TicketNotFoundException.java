package com.tw.util;

public class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException() {
        super("Ticket not found");
    }
    public TicketNotFoundException(String message) {

        super(message);
    }
}
