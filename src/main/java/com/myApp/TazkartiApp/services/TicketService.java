package com.myApp.TazkartiApp.services;

import com.myApp.TazkartiApp.DTO.TicketCreateDTO;
import com.myApp.TazkartiApp.DTO.TicketDTO;
import java.util.List;

public interface TicketService {
    List<TicketDTO> getAllTickets();
    TicketDTO getTicketById(Long id);
    TicketDTO createTicket(TicketCreateDTO ticketDTO);
    TicketDTO updateTicket(Long id, TicketCreateDTO ticketDTO);
    void deleteTicket(Long id, Long adminId);
}
