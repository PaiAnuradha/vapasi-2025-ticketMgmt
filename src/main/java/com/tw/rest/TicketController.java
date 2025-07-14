package com.tw.rest;

import com.tw.dto.PassengerDto;
import com.tw.dto.TicketDto;
import com.tw.entity.Ticket;
import com.tw.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    TicketService ticketService;

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return new ResponseEntity<>(ticketService.getAllTickets(), HttpStatus.OK);
    }

    @GetMapping("/{pnr}")
    public ResponseEntity<Ticket> getTicketByPnr(@PathVariable int pnr) {
        return new ResponseEntity<>(ticketService.getTicket(pnr), HttpStatus.FOUND);
    }

    @DeleteMapping("/{pnr}")
    public ResponseEntity<Boolean> deleteTicketByPnr(@PathVariable int pnr) {
        ticketService.deleteTicket(pnr);
        return new ResponseEntity<>(true,HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Ticket> createTicketWithPassengers(@RequestBody @Valid TicketDto dto) {
        Ticket createdTicket = ticketService.createTicketWithPassengers(dto);
        return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
    }

    @DeleteMapping("/{pnr}/passengers/{aadhar}")
    public ResponseEntity<String> deletePassengerFromTicket(@PathVariable int pnr,
                                                            @PathVariable String aadhar) {
        ticketService.deletePassengerFromTicket(pnr, aadhar);
        return ResponseEntity.ok("Passenger removed from ticket: " + pnr);
    }

    @PostMapping(value = "/{pnr}/passengers", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Ticket> addPassengerToTicket(@PathVariable int pnr,
                                                       @RequestBody @Valid PassengerDto dto) {
        Ticket ticket = ticketService.addPassengerToTicket(dto, pnr);
        return new ResponseEntity<>(ticket, HttpStatus.CREATED);
    }
}
