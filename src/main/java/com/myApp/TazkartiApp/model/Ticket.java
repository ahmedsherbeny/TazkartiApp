package com.myApp.TazkartiApp.model;

import com.myApp.TazkartiApp.Enums.TicketStatus;
import com.myApp.TazkartiApp.services.TicketServiceImpl;
import lombok.*;
import jakarta.persistence.*;

import java.lang.reflect.Type;

@Entity
@Table(name = "tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatNumber;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;
}


