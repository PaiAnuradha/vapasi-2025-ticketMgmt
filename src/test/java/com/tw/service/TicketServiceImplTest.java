package com.tw.service;

import com.tw.dto.PassengerDto;
import com.tw.dto.TicketDto;
import com.tw.entity.Passenger;
import com.tw.entity.Ticket;
import com.tw.exception.MaxPassengersExceededException;
import com.tw.exception.TicketNotFoundException;
import com.tw.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceImplTest {
    @Mock
    private PassengerService passengerService;
    @Mock
    private TicketRepository ticketRepository;
    @InjectMocks
    private TicketServiceImpl ticketService;


    /*
    Ticket createTicketWithPassengers(TicketDto dto);
    void deletePassengerFromTicket(int pnr, String aadhar);
    Ticket addPassengerToTicket(PassengerDto passengerDto, int pnr);
     */



    @Test
    void testGetAllTickets() {
        List<Ticket> ticketList = Arrays.asList(new Ticket(), new Ticket());
        when(ticketRepository.findAll()).thenReturn(ticketList);

        List<Ticket> result = ticketService.getAllTickets();

        assertEquals(2, result.size());
    }
    @Test
    void testGetAllTicketsOnEmptyTable() {
        when(ticketRepository.findAll()).thenReturn(List.of());
        assertThrows(TicketNotFoundException.class, () -> ticketService.getAllTickets());
    }

    @Test
    void testGetTicketById() {
        Ticket ticket = new Ticket();
        ticket.setPnr(1);
        ticket.setSource("source");
        ticket.setDestination("destination");
        ticket.setTravelDate(LocalDate.now());

        when(ticketRepository.findById(String.valueOf(1))).thenReturn(Optional.of(ticket));
        Ticket result = ticketService.getTicket(1);
        assertNotNull(result);
        assertEquals(ticket.getPnr(), result.getPnr());
        assertEquals(ticket.getSource(), result.getSource());
        assertEquals(ticket.getDestination(), result.getDestination());
        assertEquals(ticket.getTravelDate(), result.getTravelDate());
    }

    @Test
    void testGetTicketByIdNotFound() {
        when(ticketRepository.findById(String.valueOf(1))).thenReturn(Optional.empty());
        assertThrows(TicketNotFoundException.class, () -> ticketService.getTicket(1));
    }

    @Test
    void testDeleteTicketById() {
        Ticket ticket = new Ticket();
        ticket.setPnr(1);
        when(ticketRepository.findById(String.valueOf(1))).thenReturn(Optional.of(ticket));
        doNothing().when(ticketRepository).deleteById(String.valueOf(1));
        ticketService.deleteTicket(1);
        verify(ticketRepository, times(1)).deleteById(String.valueOf(1));
    }

    @Test
    void testDeleteOnNonExistentTicket() {
        when(ticketRepository.findById(String.valueOf(1))).thenReturn(Optional.empty());
        assertThrows(TicketNotFoundException.class, () -> ticketService.deleteTicket(1));
    }

    @Test
    void testCreateTicketWithMoreThanTenPassengers() {
        List<PassengerDto> passengerDtoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            passengerDtoList.add(new PassengerDto("11223344556"+i, "Name"+i, 20, "FEMALE"));
        }
        passengerDtoList.add(new PassengerDto("112233445511", "Name11", 20, "MALE"));
        passengerDtoList.add(new PassengerDto("112233445512", "Name12", 20, "MALE"));

        TicketDto ticketDto = new TicketDto();
        ticketDto.setPassengers(passengerDtoList);

        assertThrows(MaxPassengersExceededException.class, () -> ticketService.createTicketWithPassengers(ticketDto));
    }

    @Test
    void testCreateTicketForExistingPassenger() {
        List<PassengerDto> passengerDtoList = new ArrayList<>();
        passengerDtoList.add(new PassengerDto("112233445511", "Name11", 20, "MALE"));
        passengerDtoList.add(new PassengerDto("112233445512", "Name12", 20, "MALE"));

        TicketDto ticketDto = new TicketDto();
        ticketDto.setPassengers(passengerDtoList);

        //when(ticketRepository.existsById())
    }

    @Test
    void testCreateTicket(){
        TicketDto ticketDto = new TicketDto();
        ticketDto.setTravelDate(LocalDate.now().toString());
        ticketDto.setSource("source");
        ticketDto.setDestination("destination");

        PassengerDto dto1 = new PassengerDto("123456789012", "John", 30, "MALE");
        PassengerDto dto2 = new PassengerDto("987654321098", "Jane", 28, "FEMALE");

        ticketDto.setPassengers(Arrays.asList(dto1, dto2));

    }
}