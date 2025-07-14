package com.tw.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class TicketDto {

    @NotBlank(message = "Source cannot be null")
    private String source;
    @NotBlank(message = "Destination cannot be null")
    private String destination;
    @NotBlank(message = "Travel date cannot be null and should be in the format yyyy-mm-dd")
    private String travelDate;
    @Valid
    @NotEmpty(message = "At least one passenger is required")
    private List<PassengerDto> passengers;
}
