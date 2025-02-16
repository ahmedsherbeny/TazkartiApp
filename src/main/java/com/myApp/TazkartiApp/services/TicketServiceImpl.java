package com.myApp.TazkartiApp.services;

import com.myApp.TazkartiApp.DTO.TicketDTO;
import com.myApp.TazkartiApp.model.Ticket;
import com.myApp.TazkartiApp.Repositories.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

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
    public TicketDTO createTicket(TicketDTO ticketDTO) {
        Ticket ticket = mapToEntity(ticketDTO);
        Ticket savedTicket = ticketRepository.save(ticket);
        return mapToDTO(savedTicket);
    }

    @Override
    public TicketDTO updateTicket(Long id, TicketDTO ticketDTO) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id " + id));

        if (ticketDTO.getSeatNumber() != null) {
            ticket.setSeatNumber(ticketDTO.getSeatNumber());
        }
        if (ticketDTO.getPrice() != null) {
            ticket.setPrice(ticketDTO.getPrice());
        }
        // في حال أردت تحديث الـ eventId أو userId، يجب إضافة منطق إضافي لجلب الكيانات
        Ticket updatedTicket = ticketRepository.save(ticket);
        return mapToDTO(updatedTicket);
    }

    @Override
    public void deleteTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id " + id));
        ticketRepository.delete(ticket);
    }

    private TicketDTO mapToDTO(Ticket ticket) {
        TicketDTO dto = new TicketDTO();
        dto.setId(ticket.getId());
        dto.setSeatNumber(ticket.getSeatNumber());
        dto.setPrice(ticket.getPrice());
        dto.setEventId(ticket.getEvent().getId());
        dto.setUserId(ticket.getUser().getId());
        return dto;
    }

    private Ticket mapToEntity(TicketDTO dto) {
        // هنا ينبغي جلب كائنات الـ Event والـ User باستخدام الـ eventId و userId.
        // لأغراض التبسيط، سنبقيها null. في التطبيق الحقيقي يجب جلب الكيانات المناسبة.
        Ticket ticket = new Ticket();
        ticket.setSeatNumber(dto.getSeatNumber());
        ticket.setPrice(dto.getPrice());
        return ticket;
    }
}
