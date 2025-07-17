package com.tw.repository;

import com.tw.entity.Ticket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TicketRepositoryTest {
    @Autowired
    private TicketRepository ticketRepository;

    private int pnr = 1;

    @Test
    public void testSaveTicket(){
        Ticket ticket = Ticket.builder()
                .destination("Bengluru")
                .source("Mysore")
                .travelDate(LocalDate.now())
                .build();
        ticketRepository.save(ticket);
        assertThat(ticket.getPnr()).isGreaterThan(0);
        pnr++;
    }

    @Test
    void testfindById() {
        Ticket ticketBuilder = Ticket.builder()
                .destination("Bengluru")
                .source("Mysore")
                .travelDate(LocalDate.now())
                .build();
        Ticket ticket1 = ticketRepository.save(ticketBuilder);
        Optional<Ticket> ticketOptional = ticketRepository.findById(String.valueOf(ticket1.getPnr()));
        assertTrue(ticketOptional.isPresent());
        assertEquals(pnr, ticketOptional.get().getPnr());
        assertEquals(ticketBuilder.getTravelDate(), ticketOptional.get().getTravelDate());
        assertEquals("Bengluru", ticketOptional.get().getDestination());
        assertEquals("Mysore", ticketOptional.get().getSource());
    }

    @Test
    void testfindAll() {
        Ticket ticketBuilder = Ticket.builder()
                .destination("Bengluru")
                .source("Mysore")
                .travelDate(LocalDate.now())
                .build();
        ticketRepository.save(ticketBuilder);
        Ticket ticketBuilder2 = Ticket.builder()
                .destination("Bengluru")
                .source("Mysore")
                .travelDate(LocalDate.now())
                .build();
        ticketRepository.save(ticketBuilder2);

        List<Ticket> tickets = ticketRepository.findAll();
        assertFalse(tickets.isEmpty());
        assertEquals(2, tickets.size());
    }

    @Test
    void testfindAllForEmptyTable() {
        List<Ticket> tickets = ticketRepository.findAll();
        assertTrue(tickets.isEmpty());
    }

    @Test
    void testfindByNonExistentId() {
        Optional<Ticket> ticketOptional = ticketRepository.findById(String.valueOf(1234));
        assertFalse(ticketOptional.isPresent());
    }

    @Test
    void testDeleteById() {
        Ticket ticketBuilder = Ticket.builder()
                .destination("Bengluru")
                .source("Mysore")
                .travelDate(LocalDate.now())
                .build();
        Ticket ticket1 = ticketRepository.save(ticketBuilder);
        ticketRepository.deleteById(String.valueOf(ticket1.getPnr()));
        Optional<Ticket> ticketOptional = ticketRepository.findById(String.valueOf(ticket1.getPnr()));
        assertFalse(ticketOptional.isPresent());
    }
}
