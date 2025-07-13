package com.tw.service;

import com.tw.entity.Passenger;
import com.tw.entity.Ticket;

import java.util.List;

public interface PassengerService {
    void saveAll(List<Passenger> passengers);
    void addPassengerToTicket(Passenger passenger, Ticket ticket);
    void deletePassenger(Passenger passenger, Ticket ticket);
    boolean passengerExists(String aadhar);
}
