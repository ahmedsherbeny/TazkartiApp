package com.myApp.TazkartiApp.DTO;

import com.myApp.TazkartiApp.Enums.EventType;
import com.myApp.TazkartiApp.model.Ticket;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
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
    private LocalTime clock ;
    private String description;
    private Double price;
    private EventType eventType;
}