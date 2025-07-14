package com.tw.service;

import com.tw.dto.PassengerDto;
import com.tw.dto.TicketDto;
import com.tw.entity.Passenger;
import com.tw.entity.Ticket;
import com.tw.exception.MaxPassengersExceededException;
import com.tw.exception.PassengerAlreadyExistsException;
import com.tw.exception.TicketNotFoundException;
import com.tw.repository.TicketRepository;
import com.tw.util.DtoMapperUtil;
import com.tw.util.Gender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
            passengerDtoList.add(new PassengerDto("11223344556" + i, "Name" + i, 20, "FEMALE"));
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

        when(passengerService.passengerExists(anyString())).thenReturn(true);

        assertThrows(PassengerAlreadyExistsException.class, () -> ticketService.createTicketWithPassengers(ticketDto));
    }

    @Test
    void testCreateTicket() {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setTravelDate(LocalDate.now().toString());
        ticketDto.setSource("source");
        ticketDto.setDestination("destination");

        PassengerDto dto1 = new PassengerDto("123456789012", "John", 30, "MALE");
        PassengerDto dto2 = new PassengerDto("987654321098", "Jane", 28, "FEMALE");
        ticketDto.setPassengers(List.of(dto1, dto2));
        when(passengerService.passengerExists(anyString())).thenReturn(false);

        Ticket ticket = DtoMapperUtil.mapToTicket(ticketDto);
        Passenger p1 = DtoMapperUtil.mapToPassenger(dto1, ticket);
        Passenger p2 = DtoMapperUtil.mapToPassenger(dto2, ticket);
        ticket.setPassengers(List.of(p1, p2));

        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);
        ticketService.createTicketWithPassengers(ticketDto);
        verify(ticketRepository, times(1)).save(any(Ticket.class));
        assertNotNull(ticket);
    }

    @Test
    void testAddPassengerToNonExistentTicket() {
        when(ticketRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(TicketNotFoundException.class, () -> ticketService.addPassengerToTicket(new PassengerDto(), 1));
    }

    @Test
    void testAddPassengerToTicketWithMaxPassengers() {
        Ticket ticket = new Ticket();
        ticket.setPnr(1);
        List<Passenger> passengerList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Passenger passenger = new Passenger();
            passenger.setTicket(ticket);
            passenger.setGender(Gender.FEMALE);
            passenger.setAadhar("11223344556" + i);
            passenger.setName("Name" + i);
            passengerList.add(passenger);
        }
        ticket.setPassengers(passengerList);
        when(ticketRepository.findById(String.valueOf(1))).thenReturn(Optional.of(ticket));
        assertThrows(MaxPassengersExceededException.class, () -> ticketService.addPassengerToTicket(new PassengerDto(), 1));
    }

    @Test
    void testAddPassengerToTicketWithExistingPassengers() {
        PassengerDto dto1 = new PassengerDto("123456789012", "John", 30, "MALE");

        when(passengerService.passengerExists(anyString())).thenReturn(true);
        Ticket ticket = new Ticket();
        ticket.setPnr(1);
        ticket.setPassengers(List.of(new Passenger()));
        when(ticketRepository.findById(anyString())).thenReturn(Optional.of(ticket));
        assertThrows(PassengerAlreadyExistsException.class, () -> ticketService.addPassengerToTicket(dto1, 1));
    }

    @Test
    void testAddPassengerToTicket() {
        PassengerDto dto1 = new PassengerDto("123456789012", "John", 30, "MALE");
        Ticket ticket = new Ticket();
        ticket.setPnr(1);

        when(ticketRepository.findById(anyString())).thenReturn(Optional.of(ticket));
        when(passengerService.passengerExists(anyString())).thenReturn(false);
        List<Passenger> passengerList = new ArrayList<>();
        Passenger passenger = new Passenger();
        passenger.setTicket(ticket);
        passenger.setGender(Gender.FEMALE);
        passenger.setAadhar("112233445566");
        passenger.setName("Name");
        passengerList.add(passenger);
        ticket.setPassengers(passengerList);

        when(ticketRepository.save(ticket)).thenReturn(ticket);
        Ticket result = ticketService.addPassengerToTicket(dto1, 1);
        assertEquals(2, result.getPassengers().size());
    }

    @Test
    void testDeleteLastPassengerFromTicket() {
        Ticket ticket = new Ticket();
        ticket.setPnr(1);
        ticket.setPassengers(new ArrayList<>());
        when(ticketRepository.findById(anyString())).thenReturn(Optional.of(ticket));
        doNothing().when(passengerService).deletePassenger(anyString(), any(Ticket.class));
        ticketService.deletePassengerFromTicket(1, "");
        verify(ticketRepository, times(1)).deleteById(anyString());
    }

}