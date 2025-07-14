package com.tw.rest;

import com.tw.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<?> handleTicketNotFoundException(TicketNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.create(e, HttpStatus.NOT_FOUND, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MaxPassengersExceededException.class)
    public ResponseEntity<?> handleMaxPassengerAddedException(MaxPassengersExceededException e) {
        ErrorResponse errorResponse = ErrorResponse.create(e, HttpStatus.CONFLICT, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PassengerAlreadyExistsException.class)
    public ResponseEntity<?> handlePassengerAlreadyExistsException(PassengerAlreadyExistsException e) {
        ErrorResponse errorResponse = ErrorResponse.create(e, HttpStatus.CONFLICT, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PassengerNotFoundException.class)
    public ResponseEntity<?> handlePassengerNotFoundException(PassengerNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.create(e, HttpStatus.NOT_FOUND, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(PassengerNotLinkedToTicketException.class)
    public ResponseEntity<?> handlePassengerNotLinkedToTicketException(PassengerNotLinkedToTicketException e) {
        ErrorResponse errorResponse = ErrorResponse.create(e, HttpStatus.CONFLICT, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}
