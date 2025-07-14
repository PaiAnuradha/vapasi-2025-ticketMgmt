package com.tw.service;

import com.tw.entity.Passenger;
import com.tw.entity.Ticket;
import com.tw.exception.PassengerNotFoundException;
import com.tw.exception.PassengerNotLinkedToTicketException;
import com.tw.repository.PassengerRepository;
import com.tw.util.Gender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PassengerServiceImplTest {
    @InjectMocks
    private PassengerServiceImpl passengerService;
    @Mock
    private PassengerRepository passengerRepository;

    @Test
    void testFindPassengerById() {
        Passenger mockPassenger = new Passenger();
        String aadhar = "123456789012";
        mockPassenger.setAadhar(aadhar);
        mockPassenger.setName("Jane Smith");
        mockPassenger.setAge(25);
        mockPassenger.setGender(Gender.FEMALE);

        when(passengerRepository.findById(aadhar)).thenReturn(Optional.of(mockPassenger));

        Passenger passenger = passengerService.findById(aadhar);
        assertNotNull(passenger);
        assertEquals(aadhar, passenger.getAadhar());
        assertEquals(mockPassenger.getName(), passenger.getName());
        assertEquals(mockPassenger.getAge(), passenger.getAge());
        assertEquals(mockPassenger.getGender(), passenger.getGender());
    }

    @Test
    void testFindPassengerForNonExistentId() {
        String aadhar = "123456789012";
        when(passengerRepository.findById(aadhar)).thenReturn(Optional.empty());
        assertThrows(PassengerNotFoundException.class, () -> passengerService.findById(aadhar));
    }

    @Test
    void testDeletePassenger() {
        Passenger mockPassenger1 = new Passenger();
        String aadhar1 = "123456789012";
        mockPassenger1.setAadhar(aadhar1);
        mockPassenger1.setName("Jane Smith");
        mockPassenger1.setAge(25);
        mockPassenger1.setGender(Gender.FEMALE);

        Ticket mockTicket = new Ticket();
        mockTicket.setPnr(1);

        mockPassenger1.setTicket(mockTicket);

        List<Passenger> passengerList = new ArrayList<>();
        passengerList.add(mockPassenger1);

        mockTicket.setPassengers(passengerList);

        when(passengerRepository.findById(aadhar1)).thenReturn(Optional.of(mockPassenger1));
        doNothing().when(passengerRepository).delete(mockPassenger1);

        passengerService.deletePassenger(mockPassenger1.getAadhar(), mockTicket);

        assertFalse(mockTicket.getPassengers().contains(mockPassenger1));
        assertNull(mockPassenger1.getTicket());
        verify(passengerRepository, times(1)).delete(mockPassenger1);
    }

    @Test
    void testDeletePassengerForNonExistentId() {
        when(passengerRepository.findById("1234567887654321")).thenReturn(Optional.empty());
        assertThrows(PassengerNotFoundException.class, () -> passengerService.deletePassenger("1234567887654321", new Ticket()));
    }

    @Test
    void testDeleteForPassengerNotInTicket() {
        Passenger mockPassenger1 = new Passenger();
        String aadhar1 = "123456789012";
        mockPassenger1.setAadhar(aadhar1);
        mockPassenger1.setName("Jane Smith");
        mockPassenger1.setAge(25);
        mockPassenger1.setGender(Gender.FEMALE);

        Ticket mockTicket = new Ticket();
        mockTicket.setPnr(1);

        Ticket mockTicket2 = new Ticket();
        mockTicket.setPnr(2);
        mockPassenger1.setTicket(mockTicket);


        when(passengerRepository.findById(aadhar1)).thenReturn(Optional.of(mockPassenger1));
        assertThrows(PassengerNotLinkedToTicketException.class, () -> passengerService.deletePassenger(aadhar1, mockTicket2));
    }

    @Test
    void testPassengerExistsTrue() {

        String aadhar = "123456789012";
        when(passengerRepository.existsById(aadhar)).thenReturn(true);
        assertTrue(passengerService.passengerExists(aadhar));
    }

    @Test
    void testPassengerExistsFalse() {
        String aadhar = "123456789012";
        when(passengerRepository.existsById(aadhar)).thenReturn(false);
        assertFalse(passengerService.passengerExists(aadhar));
    }
}