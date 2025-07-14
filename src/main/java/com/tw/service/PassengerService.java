package com.tw.service;

import com.tw.entity.Passenger;
import com.tw.entity.Ticket;

public interface PassengerService {
    Passenger findById(String aadhar);
    void deletePassenger(String aadhar, Ticket ticket);
    boolean passengerExists(String aadhar);
}
