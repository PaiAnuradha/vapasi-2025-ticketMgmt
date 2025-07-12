package com.tw.rest;

import com.tw.entity.Passenger;
import com.tw.entity.Ticket;
import com.tw.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private PassengerRepository passengerRepository;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        return null;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return null;
    }

    @GetMapping(value = "/{pnr}", produces = "application/json")
    public ResponseEntity<Ticket> getTicket(@PathVariable("pnr") Integer pnr) {
        return null;
    }

    @DeleteMapping(value = "/{pnr}")
    public ResponseEntity<Boolean> deleteTicket(@PathVariable("pnr") Integer pnr) {
        return null;
    }

    @PostMapping(value = "/{pnr}/passengers", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Ticket> addPassenger(@RequestBody Passenger passenger,
                                               @PathVariable("pnr") Integer pnr) {
        return null;
    }

    @DeleteMapping(value = "/{pnr}/passengers/{aadhar}")
    public ResponseEntity<Boolean> deletePassengerFromTicket(@PathVariable("aadhar") Integer aadhar,
                                                             @PathVariable("pnr")  Integer pnr) {
        return null;
    }
}
