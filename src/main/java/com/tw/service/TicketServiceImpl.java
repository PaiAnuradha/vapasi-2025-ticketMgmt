package com.tw.service;

import com.tw.dto.TicketPassengerDto;
import com.tw.entity.Passenger;
import com.tw.entity.Ticket;
import com.tw.repository.PassengerRepository;
import com.tw.repository.TicketRepository;
import com.tw.util.MaxPassengersAddedException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    TicketRepository repoTicket;

    @Autowired
    PassengerRepository repoPassenger;

    @Autowired
    PassengerService service;

    private boolean validatePassengerCount(List<Passenger> passengers) {
        return passengers.size() <= 10 ;
    }

    @Transactional
    @Override
    public Ticket addTicket(TicketPassengerDto dto) {
        /*Step1: Check wehther there are not more than 10passengers
        Step2: Check whether there is minimum one passenger
        Step2: Check if he is alrady part of some other ticket
         */
        if(validatePassengerCount(dto.getPassengers()))
        {
            if(dto.getPassengers().size() <= 0)
            {
                throw new IllegalStateException("Passenger list size should be atleast 1");// todo exception
            }
            boolean duplicateExists = dto.getPassengers().stream()
                    .anyMatch(p -> repoPassenger.existsById(p.getAadhar()));

            if (duplicateExists) {
                throw new IllegalStateException("One or more Aadhaar numbers already exist.");
            }
            Ticket ticket = new Ticket();
            ticket.setSource(dto.getSource());
            ticket.setDestination(dto.getDestination());
            ticket.setTravelDate(dto.getTravelDate());
            Ticket ticketObj = repoTicket.save(ticket);
            dto.getPassengers().forEach(passenger -> {
                passenger.setTicket(ticketObj);
            });
            service.saveAll(dto.getPassengers());
            return ticket;
        }
        else{
            //throw an exception
            throw new MaxPassengersAddedException();
        }
    }
    public int  deleteTicketByPnr(int pnr) {
      repoTicket.deleteById(String.valueOf(pnr));
      return 1;
    }

    public void addPassengerToTicket(int pnr, Passenger passenger) {
        /* Check whether the pnr is valid
        Check whether the ticket already has maximuk number of passenger
        Check whether the passenger belongs to only one ticket - i.e aadhar number not exisits - primary key constraint takes care of it

         */
        /*Condition1*/
        Ticket ticket = repoTicket.findById(String.valueOf(pnr)).orElseThrow(
                () -> new IllegalArgumentException("Ticket with PNR " + pnr + " not found"));

        // Business Rule: Max 10 passengers per ticket
        if (ticket.getPassengers().size() >= 10) {
            throw new IllegalStateException("Cannot add more than 10 passengers to this ticket.");
        }

        // Business Rule: One ticket per passenger //Todo

        passenger.setTicket(ticket);
        ticket.getPassengers().add(passenger); // maintain bidirectional consistency





    }

    public void deletePassengerFromTicket(int pnr, String aadhar) {
        /*
        Step1: Check whether the pnr exisits
        Step2: whether the aadhar exists
        Step3: Check whether passenger belongs to that aadhar
        Step4: Update collection in  ticket, remove bidirectional ref and delete the passenger
        Step5: Delete the ticket if you are removing tha last passenger
         */
        Ticket ticket = repoTicket.findById(String.valueOf(pnr))
                .orElseThrow(() -> new RuntimeException("Ticket with PNR " + pnr + " not found"));

        Passenger passenger = repoPassenger.findById(String.valueOf(aadhar))
                .orElseThrow(() -> new RuntimeException("Passenger with Aadhaar " + aadhar + " not found"));

        if (!(passenger.getTicket().getPnr() == pnr) ){
            throw new IllegalStateException("Passenger does not belong to ticket " + pnr);
        }

        // Maintain bidirectional consistency
        ticket.getPassengers().remove(passenger);
        passenger.setTicket(null);

        repoPassenger.delete(passenger);

        // If this was the last passenger, delete the ticket too
        if (ticket.getPassengers().isEmpty()) {
            repoTicket.delete(ticket);
        }
    }
}
