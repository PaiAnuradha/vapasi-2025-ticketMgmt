package com.tw.repository;

import com.tw.entity.Passenger;
import com.tw.entity.Ticket;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, String> {

    @Modifying
    @Transactional
    @Query("DELETE from Ticket t WHERE t.pnr = :pnr ")
    public int deleteTicketsByPnr(@Param("pnr") int pnr);
}
