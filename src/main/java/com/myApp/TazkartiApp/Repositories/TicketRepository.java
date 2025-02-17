package com.myApp.TazkartiApp.Repositories;

import com.myApp.TazkartiApp.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    boolean existsBySeatNumberAndEventId(String seatNumber ,Long eventId);
}
