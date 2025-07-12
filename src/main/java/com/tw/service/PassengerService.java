package com.tw.service;

import com.tw.entity.Passenger;

import java.util.List;

public interface PassengerService {
    Passenger save(Passenger passenger);
    boolean delete(Passenger passenger);
    List<Passenger> findAll();
}
