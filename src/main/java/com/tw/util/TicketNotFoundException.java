package com.tw.util;

public class TicketNotFoundException extends RuntimeException {
    TicketNotFoundException() {

    }
    public TicketNotFoundException(String message) {
        super(message);
    }
}
