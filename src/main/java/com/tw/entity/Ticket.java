package com.tw.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name="ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pnr")
    int pnr;
    @Column(length=30)
    String source;
    @Column(length=30)
    String destination;
    @Column(name="travel_date")
    LocalDate travelDate;
}
