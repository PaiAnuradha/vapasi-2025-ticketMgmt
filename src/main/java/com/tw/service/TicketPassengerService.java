package com.tw.service;

import com.tw.dto.PassengerDto;
import com.tw.dto.TicketDto;
import com.tw.entity.Passenger;
import com.tw.entity.Ticket;


public interface TicketPassengerService {
    Ticket createTicketWithPassengers(TicketDto dto);
    void deletePassengerFromTicket(int pnr, String aadhar);
    Ticket addPassengerToTicket(PassengerDto passengerDto, int pnr);
}
