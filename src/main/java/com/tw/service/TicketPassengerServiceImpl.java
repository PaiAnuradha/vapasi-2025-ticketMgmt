package com.tw.service;

import com.tw.dto.PassengerDto;
import com.tw.dto.TicketDto;
import com.tw.entity.Passenger;
import com.tw.entity.Ticket;
import com.tw.repository.PassengerRepository;
import com.tw.repository.TicketRepository;
import com.tw.util.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TicketPassengerServiceImpl implements TicketPassengerService {
    @Autowired
    private TicketService ticketService;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private TicketRepository ticketRepository;

    private Ticket mapToTicket(TicketDto dto) {
        Ticket ticket = new Ticket();
        ticket.setSource(dto.getSource());
        ticket.setDestination(dto.getDestination());
        ticket.setTravelDate(LocalDate.parse(dto.getTravelDate()));
        return ticket;
    }

    private List<Passenger> mapToPassengers(List<PassengerDto> passengerDtoList, Ticket ticket) {
        return passengerDtoList.stream()
                .map(dto -> {
                    Passenger p = new Passenger();
                    p.setAadhar(dto.getAadhar());
                    p.setName(dto.getName());
                    p.setGender(parseGender(dto.getGender()));
                    p.setAge(dto.getAge());
                    p.setTicket(ticket);
                    return p;
                })
                .collect(Collectors.toList());
    }

    private Gender parseGender(String input) {
        try {
            return Gender.valueOf(input.trim().toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid gender: " + input);
        }
    }

    @Transactional
    public Ticket createTicketWithPassengers(TicketDto dto) {
        if(dto.getPassengers().size() > 10 ) //todo hardcode
            throw new MaxPassengersExceededException("Max passenger for a ticket is 10");

        boolean duplicateExists = dto.getPassengers().stream()
                .anyMatch(dtoPassenger -> passengerService.passengerExists(dtoPassenger.getAadhar()));

        if (duplicateExists)
            throw new PassengerAlreadyExistsException("Passenger already exists");

        Ticket ticket = mapToTicket(dto);
        List<Passenger> passengers = mapToPassengers(dto.getPassengers(), ticket);
        ticket.setPassengers(passengers);
        return ticketRepository.save(ticket);
    }

    private void validatePassengerList(List<Passenger> passengers) {
        if (passengers == null || passengers.isEmpty())
            throw new PassengerNotFoundException("Passenger list is empty");
        if (passengers.size() > 10)
            throw new MaxPassengersExceededException();
    }
}

