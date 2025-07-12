package com.tw.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name="ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pnr")
    int pnr;

    @Column(length=30, nullable=false)
    String source;

    @Column(length=30, nullable=false)
    String destination;

    @Column(name="travel_date", nullable=false)
    LocalDate travelDate;

    @ManyToMany
    @JoinTable(name = "ticket_passenger", joinColumns = @JoinColumn(name = "pnr"),
            inverseJoinColumns = @JoinColumn(name = "aadhdar"))
    List<Passenger> passengers;
}
