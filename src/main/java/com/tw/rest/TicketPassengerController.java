package com.tw.rest;

import com.tw.dto.TicketDto;
import com.tw.entity.Ticket;
import com.tw.service.TicketPassengerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class TicketPassengerController {

    @Autowired
    private TicketPassengerService ticketPassengerService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Ticket> createTicketWithPassengers(@RequestBody @Valid TicketDto dto) {
        Ticket createdTicket = ticketPassengerService.createTicketWithPassengers(dto);
        return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
    }

    /**
     * Remove a passenger from the given ticket
     */
//    @DeleteMapping("/{pnr}/passengers/{aadhar}")
//    public ResponseEntity<String> deletePassengerFromTicket(@PathVariable int pnr,
//                                                            @PathVariable String aadhar) {
//        ticketPassengerService.deletePassengerFromTicket(pnr, aadhar);
//        return ResponseEntity.ok("Passenger removed from ticket: " + pnr);
//    }
}
