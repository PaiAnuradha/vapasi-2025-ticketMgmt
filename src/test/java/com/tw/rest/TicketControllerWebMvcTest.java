package com.tw.rest;

import com.tw.dto.PassengerDto;
import com.tw.dto.TicketDto;
import com.tw.entity.Passenger;
import com.tw.entity.Ticket;
import com.tw.service.TicketService;
import com.tw.util.DtoMapperUtil;
import com.tw.util.Gender;
import com.tw.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TicketController.class)
public class TicketControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TicketService ticketService;

    private Ticket mockTicket;

    private PassengerDto passengerDto;

    @BeforeEach
    public void setUp() {
        Passenger passenger1 = new Passenger();
        passenger1.setAadhar("111122223333");
        passenger1.setName("Nithya");
        passenger1.setGender(Gender.FEMALE);
        passenger1.setAge(25);

        Passenger passenger2 = new Passenger();
        passenger2.setAadhar("444455556666");
        passenger2.setName("Anu");
        passenger2.setGender(Gender.MALE);
        passenger2.setAge(30);

        mockTicket = new Ticket();
        mockTicket.setPnr(2);
        mockTicket.setSource("Delhi");
        mockTicket.setDestination("Goa");
        mockTicket.setTravelDate(LocalDate.of(2025, 8, 1));
        mockTicket.setPassengers(List.of(passenger1, passenger2));

        passengerDto = new PassengerDto();
        passengerDto.setName("Sathya");
        passengerDto.setAadhar("112233445566");
        passengerDto.setGender("Female");
        passengerDto.setAge(30);
    }

    @Test
    public void testGetAllTickets() throws Exception {
        when(ticketService.getAllTickets()).thenReturn(List.of(mockTicket));

        mockMvc.perform(get("/tickets")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

    }

    @Test
    public void testGetTicketByPnr() throws Exception {
        when(ticketService.getTicket(2)).thenReturn(mockTicket);

        ResultActions response = mockMvc.perform(get("/tickets/2"));
        response.andExpect(status().isFound());
    }

    @Test
    public void testDeleteTicketByPnr() throws Exception {
        doNothing().when(ticketService).deleteTicket(2);

        mockMvc.perform(delete("/tickets/2"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateTicketWithPassengers() throws Exception {
        when(ticketService.createTicketWithPassengers(any(TicketDto.class))).thenReturn(mockTicket);

        mockMvc.perform(post("/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.asJsonString(DtoMapperUtil.mapToTicketDto(mockTicket))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.pnr").value(2));
    }

    @Test
    public void testDeletePassengerFromTicket() throws Exception {
        doNothing().when(ticketService).deletePassengerFromTicket(2, "999988887777");

        mockMvc.perform(delete("/tickets/2/passengers/999988887777"))
                .andExpect(status().isOk())
                .andExpect(content().string("Passenger removed from ticket: 2"));
    }

    @Test
    public void testAddPassengerToTicket() throws Exception {
        when(ticketService.addPassengerToTicket(any(PassengerDto.class), eq(2))).thenReturn(mockTicket);

        mockMvc.perform(post("/tickets/2/passengers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.asJsonString(passengerDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.pnr").value(2));
    }

}
