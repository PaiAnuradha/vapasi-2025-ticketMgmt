package com.tw.rest;

import com.tw.util.MaxPassengersAddedException;
import com.tw.util.TicketNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<?> handleTicketNotFoundException(TicketNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.create(e, HttpStatus.NOT_FOUND, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MaxPassengersAddedException.class)
    public ResponseEntity<?> handleMaxPassengerAddedException(MaxPassengersAddedException e) {
        ErrorResponse errorResponse = ErrorResponse.create(e, HttpStatus.CONFLICT, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}
