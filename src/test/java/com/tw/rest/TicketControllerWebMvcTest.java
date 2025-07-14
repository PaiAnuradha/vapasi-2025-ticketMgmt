package com.tw.rest;

import com.tw.entity.Passenger;
import com.tw.entity.Ticket;
import com.tw.service.TicketService;
import com.tw.util.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TicketController.class)
public class TicketControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TicketService ticketService;

    private Ticket mockTicket;

    @BeforeEach
    public void setUp() {
        Passenger p1 = new Passenger();
        p1.setAadhar("111122223333");
        p1.setName("Alice");
        p1.setGender(Gender.FEMALE);
        p1.setAge(25);

        Passenger p2 = new Passenger();
        p2.setAadhar("444455556666");
        p2.setName("Bob");
        p2.setGender(Gender.MALE);
        p2.setAge(30);

        mockTicket = new Ticket();
        mockTicket.setPnr(2);
        mockTicket.setSource("Delhi");
        mockTicket.setDestination("Goa");
        mockTicket.setTravelDate(LocalDate.of(2025, 8, 1));
        mockTicket.setPassengers(List.of(p1, p2));
    }

    @Test
    public void testGetAllTickets() throws Exception {
        when(ticketService.getAllTickets()).thenReturn(List.of(mockTicket));

        mockMvc.perform(get("/tickets")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].pnr").value(2))
                .andExpect(jsonPath("$[0].source").value("Delhi"))
                .andExpect(jsonPath("$[0].destination").value("Goa"))
                .andExpect(jsonPath("$[0].travelDate").value("2025-08-01"))
                .andExpect(jsonPath("$[0].passengers[0].aadhar").value("111122223333"))
                .andExpect(jsonPath("$[0].passengers[0].name").value("Alice"))
                .andExpect(jsonPath("$[0].passengers[0].gender").value("FEMALE"))
                .andExpect(jsonPath("$[0].passengers[0].age").value(25))
                .andExpect(jsonPath("$[0].passengers[1].aadhar").value("444455556666"))
                .andExpect(jsonPath("$[0].passengers[1].name").value("Bob"))
                .andExpect(jsonPath("$[0].passengers[1].gender").value("MALE"))
                .andExpect(jsonPath("$[0].passengers[1].age").value(30));
    }

    @Test
    public void testGetTicketByPnr() throws Exception {
        when(ticketService.getTicket(2)).thenReturn(mockTicket);

        mockMvc.perform(get("/tickets/2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound());
    }
}
