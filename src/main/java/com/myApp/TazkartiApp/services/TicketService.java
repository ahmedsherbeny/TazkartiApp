package com.myApp.TazkartiApp.services;

import com.myApp.TazkartiApp.DTO.TicketDTO;
import java.util.List;

public interface TicketService {
    List<TicketDTO> getAllTickets();
    TicketDTO getTicketById(Long id);
    TicketDTO createTicket(TicketDTO ticketDTO);
    TicketDTO updateTicket(Long id, TicketDTO ticketDTO);
    void deleteTicket(Long id);
}
