package com.tw.service;

import com.tw.dto.TicketPassengerDto;
import com.tw.entity.Ticket;

import java.util.List;

public interface TicketService {
    Ticket addTicket(TicketPassengerDto dto);

    int deleteTicketByPnr(int pnr);

    void deletePassengerFromTicket(int pnr, String aadhar);

    Ticket getTicketsByPnr(int pnr);

    List<Ticket> getAllTickets();
}
