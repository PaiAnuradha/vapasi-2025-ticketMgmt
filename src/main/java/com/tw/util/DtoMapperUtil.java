package com.tw.util;

import com.tw.dto.PassengerDto;
import com.tw.dto.TicketDto;
import com.tw.entity.Passenger;
import com.tw.entity.Ticket;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.tw.util.Gender.parseGender;

public class DtoMapperUtil {
    public static Ticket mapToTicket(TicketDto dto) {
        Ticket ticket = new Ticket();
        ticket.setSource(dto.getSource());
        ticket.setDestination(dto.getDestination());
        ticket.setTravelDate(LocalDate.parse(dto.getTravelDate()));
        return ticket;
    }

    public static List<Passenger> mapToPassengers(List<PassengerDto> passengerDtoList, Ticket ticket) {
        return passengerDtoList.stream()
                .map(dto -> mapToPassenger(dto, ticket))
                .collect(Collectors.toList());
    }
    public static Passenger mapToPassenger(PassengerDto passengerDto, Ticket ticket) {
        Passenger p = new Passenger();
        p.setAadhar(passengerDto.getAadhar());
        p.setName(passengerDto.getName());
        p.setGender(parseGender(passengerDto.getGender()));
        p.setAge(passengerDto.getAge());
        p.setTicket(ticket);
        return p;
    }

    public static TicketDto mapToTicketDto(Ticket ticket) {
        TicketDto dto = new TicketDto();
        dto.setSource(ticket.getSource());
        dto.setDestination(ticket.getDestination());
        dto.setTravelDate(ticket.getTravelDate().toString()); // LocalDate → String
        dto.setPassengers(mapToPassengerDto(ticket.getPassengers())); // List<Passenger> → List<PassengerDto>
        return dto;
    }

    public static List<PassengerDto> mapToPassengerDto(List<Passenger> passengers) {
        return passengers.stream()
                .map(DtoMapperUtil::mapToPassengerDto)
                .collect(Collectors.toList());
    }

    public static PassengerDto mapToPassengerDto(Passenger passengers) {

        PassengerDto dto = new PassengerDto();
        dto.setAadhar(passengers.getAadhar());
        dto.setName(passengers.getName());
        dto.setGender(passengers.getGender().toString());
        dto.setAge(passengers.getAge());
        return dto;
    }
}