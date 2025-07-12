package com.tw.service;

import com.tw.dto.TicketPassengerDto;
import com.tw.entity.Ticket;

public interface TicketService {
    Ticket addTicket(TicketPassengerDto dto);

    int deleteTicketByPnr(int pnr);

    void deletePassengerFromTicket(int pnr, String aadhar);
}
