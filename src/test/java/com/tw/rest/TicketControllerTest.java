package com.tw.rest;

import com.tw.entity.Passenger;
import com.tw.entity.Ticket;
import com.tw.service.TicketService;
import com.tw.util.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TicketControllerTest {
    private static Ticket mockTicket;
    @InjectMocks
    private TicketController ticketController;

    @Mock
    private TicketService ticketService;

    @BeforeAll
    public static void setUp() {
        Passenger p1 = new Passenger();
        p1.setAadhar("111122223333");
        p1.setName("Alice");
        p1.setGender(Gender.FEMALE);

        Passenger p2 = new Passenger();
        p2.setAadhar("444455556666");
        p2.setName("Bob");
        p2.setGender(Gender.MALE);

        mockTicket = new Ticket();
        mockTicket.setPnr(2);
        mockTicket.setSource("Delhi");
        mockTicket.setDestination("Goa");
        mockTicket.setTravelDate(LocalDate.of(2025, 8, 1));
        mockTicket.setPassengers(List.of(p1, p2));
    }

    @Test
    public void testGetAllTickets() {
        Mockito.when(ticketService.getAllTickets()).thenReturn(List.of(mockTicket));
        // Act
        ResponseEntity<List<Ticket>> response = ticketController.getAllTickets();
        // Assert
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(1, response.getBody().size());
        Mockito.verify(ticketService).getAllTickets();
    }

    @Test
    public void testGetTicketById() {
        Mockito.when(ticketService.getTicket(2)).thenReturn(mockTicket);
        // Act
        ResponseEntity<Ticket> response = ticketController.getTicket(2);
        // Assert
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        // Assert: Check response body and values
        Ticket ticketFromResponse = response.getBody();
        Assertions.assertNotNull(ticketFromResponse);
        Assertions.assertEquals("Delhi", ticketFromResponse.getSource());
        Assertions.assertEquals("Goa", ticketFromResponse.getDestination());

        Mockito.verify(ticketService).getTicket(2);
    }

}
