package com.myApp.TazkartiApp.services;

import com.myApp.TazkartiApp.DTO.TicketDTO;
import com.myApp.TazkartiApp.Enums.TicketStatus;
import com.myApp.TazkartiApp.Repositories.EventRepository;
import com.myApp.TazkartiApp.Repositories.TicketRepository;
import com.myApp.TazkartiApp.Repositories.UserRepository;
import com.myApp.TazkartiApp.model.Event;
import com.myApp.TazkartiApp.model.Ticket;
import com.myApp.TazkartiApp.model.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public List<TicketDTO> getAllTickets() {
        return ticketRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TicketDTO getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id " + id));
        return mapToDTO(ticket);
    }

    @Override
    @Transactional
    public TicketDTO createTicket(TicketDTO ticketDTO) {
        if (ticketDTO.getUserId() == null) {
            throw new RuntimeException("User id is required");
        }
        if (ticketDTO.getSeatNumber() == null || ticketDTO.getSeatNumber().isEmpty()) {
            throw new RuntimeException("Seat number is required");
        }

        Event event = eventRepository.findById(ticketDTO.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found with id " + ticketDTO.getEventId()));
        User user = userRepository.findById(ticketDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id " + ticketDTO.getUserId()));

        boolean seatExists = ticketRepository.existsBySeatNumberAndEventId(ticketDTO.getSeatNumber(), event.getId());
        if (seatExists) {
            throw new RuntimeException("Seat " + ticketDTO.getSeatNumber() + " is already booked for this event");
        }

        Ticket ticket = new Ticket();
        ticket.setEvent(event);
        ticket.setUser(user);
        ticket.setPrice(event.getPrice());
        ticket.setSeatNumber(ticketDTO.getSeatNumber());
        ticket.setStatus(TicketStatus.AVAILABLE);
        ticket.setClock(event.getClock());

        Ticket savedTicket = ticketRepository.save(ticket);
        return mapToDTO(savedTicket);
    }

    @Override
    @Transactional
    public TicketDTO updateTicket(Long id, TicketDTO ticketDTO) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id " + id));

        if (ticketDTO.getSeatNumber() != null) ticket.setSeatNumber(ticketDTO.getSeatNumber());
        if (ticketDTO.getClock() != null) ticket.setClock(ticketDTO.getClock());

        if (ticketDTO.getEventId() != null) {
            Event event = eventRepository.findById(ticketDTO.getEventId())
                    .orElseThrow(() -> new RuntimeException("Event not found with id " + ticketDTO.getEventId()));
            ticket.setEvent(event);
        }

        if (ticketDTO.getUserId() != null) {
            User user = userRepository.findById(ticketDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id " + ticketDTO.getUserId()));
            ticket.setUser(user);
        }

        Ticket updatedTicket = ticketRepository.save(ticket);
        return mapToDTO(updatedTicket);
    }


    @Override
    @Transactional
    public void deleteTicket(Long id) {
        if (!ticketRepository.existsById(id)) {
            throw new EntityNotFoundException("Ticket not found with id : " + id);
        }
        ticketRepository.deleteById(id);
    }

    private TicketDTO mapToDTO(Ticket ticket) {
        TicketDTO dto = new TicketDTO();
        dto.setId(ticket.getId());
        dto.setSeatNumber(ticket.getSeatNumber());
        dto.setPrice(ticket.getEvent().getPrice());
        dto.setStatus(TicketStatus.AVAILABLE);
        dto.setClock(ticket.getEvent().getClock());
        dto.setEventId(ticket.getEvent().getId());
        dto.setUserId(ticket.getUser().getId());
        return dto;
    }

    private Ticket mapToEntity(TicketDTO dto) {
        Ticket ticket = new Ticket();
        Event event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id : " + dto.getEventId()));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id : " + dto.getUserId()));

        ticket.setSeatNumber(dto.getSeatNumber());
        ticket.setPrice(event.getPrice());
        ticket.setStatus(TicketStatus.AVAILABLE);
        ticket.setClock(event.getClock());
        ticket.setEvent(event);
        ticket.setUser(user);

        return ticket;
    }
}
