package com.tw.dto;

import com.tw.entity.Passenger;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TicketPassengerDto {
    private String source;
    private String destination;
    private LocalDate travelDate;
    private List<Passenger> passengers;
}
