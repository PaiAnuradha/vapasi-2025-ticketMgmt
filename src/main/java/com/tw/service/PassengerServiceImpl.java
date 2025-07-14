package com.tw.service;

import com.tw.entity.Passenger;
import com.tw.entity.Ticket;
import com.tw.repository.PassengerRepository;
import com.tw.exception.PassengerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassengerServiceImpl implements PassengerService {
    @Autowired
    private PassengerRepository passengerRepository;

    @Override
    public Passenger findById(String aadhar) {
        return passengerRepository.findById(aadhar).
                orElseThrow(() -> new PassengerNotFoundException(aadhar));
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
