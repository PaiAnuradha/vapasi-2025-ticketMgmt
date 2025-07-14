package com.tw.service;

import com.tw.dto.PassengerDto;
import com.tw.dto.TicketDto;
import com.tw.entity.Passenger;
import com.tw.entity.Ticket;
import com.tw.exception.*;
import com.tw.repository.TicketRepository;
import com.tw.util.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository repoTicket;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public List<Ticket> getAllTickets() {
        return repoTicket.findAll(); //todo exception in case empty
    }

    @Override
    public Ticket getTicket(int pnr) {
        return repoTicket.findById(String.valueOf(pnr))
                .orElseThrow(TicketNotFoundException::new);
    }

    @Override
    public void deleteTicket(int pnr) {
        Ticket ticket = getTicket(pnr);
        if (ticket == null) {
            throw new TicketNotFoundException();
        }
        repoTicket.deleteById(String.valueOf(pnr));

    }

    @Transactional
    public Ticket createTicketWithPassengers(TicketDto dto) {
        if(dto.getPassengers().size() > AppConstants.MAX_PASSENGERS )
            throw new MaxPassengersExceededException("Max passenger for a ticket is 10");

        boolean duplicateExists = dto.getPassengers().stream()
                .anyMatch(dtoPassenger -> passengerService.passengerExists(dtoPassenger.getAadhar()));

        if (duplicateExists)
            throw new PassengerAlreadyExistsException("Passenger already exists");

        Ticket ticket = DtoMapperUtil.mapToTicket(dto);
        List<Passenger> passengers = DtoMapperUtil.mapToPassengers(dto.getPassengers(), ticket);
        ticket.setPassengers(passengers);
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket addPassengerToTicket(PassengerDto passengerDto, int pnr) {
        Ticket ticket = getTicket(pnr);
        if(ticket == null) {
            throw new TicketNotFoundException("Ticket not found for PNR :: " + pnr);
        }
        if(ticket.getPassengers().size() == 10){
            throw new MaxPassengersExceededException("Max passenger for a ticket is 10");
        }
        Passenger passenger = new Passenger();
        passenger.setAadhar(passengerDto.getAadhar());
        passenger.setName(passengerDto.getName());
        passenger.setGender(Gender.parseGender(passengerDto.getGender()));
        passenger.setAge(passengerDto.getAge());
        passenger.setTicket(ticket);
        Passenger foundPassenger = null;

        try {
            foundPassenger = passengerService.findById(passenger.getAadhar());
        }  catch (Exception e) {
            //todo add logger
        }

        if(foundPassenger != null) {
            throw new PassengerAlreadyExistsException("Passenger already exists");
        }

        List<Passenger> passengerList = ticket.getPassengers();
        passengerList.add(passenger);
        ticket.setPassengers(passengerList);
        return ticketRepository.save(ticket);
    }

    @Override
    public void deletePassengerFromTicket(int pnr, String aadhar) {
        Ticket ticket = getTicket(pnr);
        Passenger passenger = passengerService.findById(aadhar);

        if (passenger.getTicket().getPnr() != pnr) {
            throw new PassengerNotLinkedToTicketException();
        }

        passengerService.deletePassenger(passenger, ticket);

        if (ticket.getPassengers().isEmpty()) {
            deleteTicket(pnr);
        }
    }
}
