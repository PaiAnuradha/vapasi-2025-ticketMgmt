package com.tw.rest;

import com.tw.entity.Ticket;
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
    TicketService ticketService;

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return new ResponseEntity<>(ticketService.getAllTickets(), HttpStatus.OK);
    }

    @GetMapping("/{pnr}")
    public ResponseEntity<Ticket> getTicket(@PathVariable int pnr) {
        return new ResponseEntity<>(ticketService.getTicket(pnr), HttpStatus.OK);
    }
}
