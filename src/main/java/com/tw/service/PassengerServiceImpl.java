package com.tw.service;

import com.tw.entity.Passenger;
import com.tw.entity.Ticket;
import com.tw.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerServiceImpl implements PassengerService {
    @Autowired
    private PassengerRepository passengerRepository;

    @Override
    public void saveAll(List<Passenger> passengers) {
        passengerRepository.saveAll(passengers);
    }

    @Override
    public void addPassengerToTicket(Passenger passenger, Ticket ticket) {
        passenger.setTicket(ticket);
        ticket.getPassengers().add(passenger);
    }

    @Override
    public void deletePassenger(Passenger passenger, Ticket ticket) {
        ticket.getPassengers().remove(passenger);
        passenger.setTicket(null);
        passengerRepository.delete(passenger);
    }

    @Override
    public boolean passengerExists(String aadhar) {
        return passengerRepository.existsById(aadhar);
    }
}
