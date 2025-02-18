package com.myApp.TazkartiApp.DTO;

import com.myApp.TazkartiApp.Enums.EventType;
import com.myApp.TazkartiApp.model.Ticket;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class EventDTO {
    private Long id;
    private String name;
    private String location;
    private LocalDate eventDate;
    private String description;
    private Double price;
    private List<Ticket> tickets;
    private EventType eventType;
}