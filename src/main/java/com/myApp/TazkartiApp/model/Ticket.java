package com.myApp.TazkartiApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myApp.TazkartiApp.Enums.TicketStatus;
import com.myApp.TazkartiApp.Enums.UserRole;
import com.myApp.TazkartiApp.services.TicketServiceImpl;
import lombok.*;
import jakarta.persistence.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
    private LocalTime clock;

    @JoinColumn(name = "admin_id", nullable = false)
    private long adminId;
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    @JsonIgnore
    private Event event;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;
}


