package com.myApp.TazkartiApp.Repositories;

import com.myApp.TazkartiApp.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    boolean existsBySeatNumberAndEventId(String seatNumber ,Long eventId);
    Optional<Ticket> findBySeatNumberAndEventId(String seatNumber, Long eventId);


}
