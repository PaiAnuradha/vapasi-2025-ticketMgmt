package com.tw.rest;

import com.tw.dto.TicketPassengerDto;
import com.tw.entity.Passenger;
import com.tw.entity.Ticket;
import com.tw.repository.PassengerRepository;
import com.tw.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketPassengerDto ticketPassengerDto) {
        ticketService.addTicket(ticketPassengerDto);
        return null;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketService.getAllTickets();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @GetMapping(value = "/{pnr}", produces = "application/json")
    public ResponseEntity<Ticket> getTicket(@PathVariable("pnr") Integer pnr) {
        Ticket ticket = ticketService.getTicketsByPnr(pnr);
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{pnr}")
    public ResponseEntity<Boolean> deleteTicket(@PathVariable("pnr") Integer pnr) {
        ticketService.deleteTicketByPnr(pnr);
        return null;
    }
}
