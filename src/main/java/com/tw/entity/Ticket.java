package com.tw.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@Entity
@Table(name="ticket")
@NoArgsConstructor
@AllArgsConstructor
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

  @OneToMany(mappedBy = "ticket", cascade =  CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  @JsonManagedReference
  List<Passenger> passengers;
}
