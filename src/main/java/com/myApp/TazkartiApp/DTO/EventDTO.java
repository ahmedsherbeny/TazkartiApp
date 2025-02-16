package com.myApp.TazkartiApp.DTO;

import lombok.*;

import java.time.LocalDateTime;

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
}