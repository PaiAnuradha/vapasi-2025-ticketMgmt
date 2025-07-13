package com.tw.repository;

import com.tw.entity.Passenger;
import com.tw.entity.Ticket;
import com.tw.util.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@DataJpaTest
class PassengerRepositoryTest {
    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Test
    public void testSavePassenger() {


        Passenger passenger = new Passenger();
        passenger.setAadhar("123456789012");
        passenger.setName("John Doe");
        passenger.setAge(30);
        passenger.setGender(Gender.MALE);
        Ticket ticket = getTicket();
        passenger.setTicket(ticket);

        Passenger savedPassenger = passengerRepository.save(passenger);

        assertNotNull(savedPassenger);
        assertEquals(passenger.getAadhar(), savedPassenger.getAadhar());
        assertEquals(passenger.getName(), savedPassenger.getName());
        assertEquals(passenger.getAge(), savedPassenger.getAge());
        assertEquals(passenger.getGender(), savedPassenger.getGender());

        assertNotNull(savedPassenger.getTicket());
        assertEquals(ticket.getPnr(), savedPassenger.getTicket().getPnr());
        assertEquals(ticket.getDestination(), savedPassenger.getTicket().getDestination());
        assertEquals(ticket.getSource(), savedPassenger.getTicket().getSource());
        assertEquals(ticket.getTravelDate(), savedPassenger.getTicket().getTravelDate());
    }

    @Test
    public void testFindByAadhar() {

        Passenger savedPassenger = getPassenger();

        Optional<Passenger> foundPassenger = passengerRepository.findById("123456789012");

        assertTrue(foundPassenger.isPresent());
        assertEquals("Jane Smith", foundPassenger.get().getName());
        assertEquals(25, foundPassenger.get().getAge());
        assertEquals(Gender.FEMALE, foundPassenger.get().getGender());
        assertEquals(savedPassenger.getAadhar(), foundPassenger.get().getAadhar());
    }

    @Test
    public void testFindByNonExistentAadhar() {
        Optional<Passenger> foundPassenger = passengerRepository.findById("1234432112344321");
        assertFalse(foundPassenger.isPresent());
    }

    @Test
    public void testDeleteByAadhar() {
        Passenger passenger = getPassenger();
        passengerRepository.delete(passenger);
        Optional<Passenger> passengerOptional =
                passengerRepository.findById(passenger.getAadhar());
        assertFalse(passengerOptional.isPresent());
    }

    private Passenger getPassenger() {
        Passenger passenger = new Passenger();
        passenger.setAadhar("123456789012");
        passenger.setName("Jane Smith");
        passenger.setAge(25);
        passenger.setGender(Gender.FEMALE);

        return passengerRepository.save(passenger);
    }

    private Ticket getTicket() {
        Ticket ticket = new Ticket();
        ticket.setDestination("Blr");
        ticket.setSource("Mys");
        ticket.setTravelDate(LocalDate.now());
        return ticketRepository.save(ticket);
    }

}