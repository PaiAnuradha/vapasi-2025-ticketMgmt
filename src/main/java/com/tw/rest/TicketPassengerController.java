package com.tw.rest;

import com.tw.dto.TicketPassengerDto;
import com.tw.entity.Passenger;
import com.tw.entity.Ticket;
import com.tw.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class TicketPassengerController {
    @Autowired
    private PassengerService passengerService;

    @PostMapping(value = "/{pnr}/passengers", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Ticket> addPassenger(@RequestBody Passenger passenger,
                                               @PathVariable("pnr") Integer pnr) {
        return null;
    }

    @DeleteMapping(value = "/{pnr}/passengers/{aadhar}")
    public ResponseEntity<Boolean> deletePassengerFromTicket(@PathVariable("aadhar") String aadhar,
                                                             @PathVariable("pnr")  Integer pnr) {
        passengerService.deletePassengerFromTicket(pnr, aadhar);
        return null;
    }
}
