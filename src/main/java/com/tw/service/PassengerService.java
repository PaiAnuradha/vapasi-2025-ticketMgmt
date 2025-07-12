package com.tw.service;

import com.tw.entity.Passenger;

import java.util.List;

public interface PassengerService {
    List<Passenger> saveAll(List<Passenger> passenger);
    boolean delete(Passenger passenger);
    List<Passenger> findAll();
    void addPassengertoTicket(Passenger passenger);
}
