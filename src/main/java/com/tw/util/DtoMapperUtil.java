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
}
