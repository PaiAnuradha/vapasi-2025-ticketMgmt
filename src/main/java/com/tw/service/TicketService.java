package com.tw.service;

import com.tw.dto.PassengerDto;
import com.tw.dto.TicketDto;
import com.tw.entity.Ticket;

import java.util.List;

public interface TicketService {
    List<Ticket> getAllTickets();
    Ticket getTicket(int pnr);
    void deleteTicket(int pnr);
    Ticket createTicketWithPassengers(TicketDto dto);
    void deletePassengerFromTicket(int pnr, String aadhar);
    Ticket addPassengerToTicket(PassengerDto passengerDto, int pnr);
}
