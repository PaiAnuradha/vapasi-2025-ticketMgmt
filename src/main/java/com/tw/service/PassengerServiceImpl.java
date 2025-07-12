package com.tw.service;

import com.tw.entity.Passenger;
import com.tw.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Service
public class PassengerServiceImpl implements PassengerService {
    @Autowired
    private PassengerRepository passengerRepository;

    @Override
    public Passenger save(Passenger passenger) {
        return null;
    }

    @Override
    public boolean delete(Passenger passenger) {
        return false;
    }

    @Override
    public List<Passenger> findAll() {
        return List.of();
    }
}
