package com.tw.service;

import com.tw.dto.TicketDto;
import com.tw.entity.Ticket;


public interface TicketPassengerService {
    Ticket createTicketWithPassengers(TicketDto dto);
   // void deletePassengerFromTicket(int pnr, String aadhar);
}
