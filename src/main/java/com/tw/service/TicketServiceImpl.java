package com.tw.service;

import com.tw.entity.Ticket;
import com.tw.repository.TicketRepository;
import com.tw.util.TicketNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository repoTicket;

    @Override
    public Ticket createTicket(Ticket ticket) {
        return repoTicket.save(ticket);
    }

    @Override
    public List<Ticket> getAllTickets() {
        return repoTicket.findAll(); //todo exception in case empty
    }

    @Override
    public Ticket getTicket(int pnr) {
        return repoTicket.findById(String.valueOf(pnr))
                .orElseThrow(TicketNotFoundException::new);
    }

    @Override
    public void deleteTicket(int pnr) {
        Ticket ticket = getTicket(pnr);
        if (ticket == null) {
            throw new TicketNotFoundException();
        }
        repoTicket.deleteById(String.valueOf(pnr));

    }
}
