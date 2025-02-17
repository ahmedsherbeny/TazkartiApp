package com.myApp.TazkartiApp.DTO;

import com.myApp.TazkartiApp.model.Ticket;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class EventDTO {
    private Long id;
    private String name;
    private String location;
    private LocalDateTime eventDate;
    private String description;
    private Double price;
    private List<Ticket> tickets;
}