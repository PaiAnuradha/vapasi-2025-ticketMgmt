package com.tw.service;

import com.tw.entity.Ticket;

import java.util.List;

public interface TicketService {
    Ticket createTicket(Ticket ticket);
    List<Ticket> getAllTickets();
    Ticket getTicket(int pnr);
    void deleteTicket(int pnr);
}
