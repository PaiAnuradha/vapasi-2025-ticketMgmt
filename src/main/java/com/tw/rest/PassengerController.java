package com.tw.rest;

import com.tw.dto.TicketPassengerDto;
import com.tw.entity.Passenger;
import com.tw.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/passenger")
public class PassengerController {
    @Autowired
    private PassengerService passengerService;

    @PostMapping(name = "/addToTicket", consumes = "application/json", produces = "application/json")
    public RequestEntity<Passenger> addPassenger(@RequestBody Passenger passenger, @RequestParam int pnr) {
        //TODO:: add passenger implementation
        return null;
    }

    @DeleteMapping(name = "/deleteFromTicket", consumes = "application/json")
    public void deletePassenger(@RequestBody TicketPassengerDto  passengerDto) {
        //TODO :: delete passenger

    }
}
