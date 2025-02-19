package com.myApp.TazkartiApp.model;

import com.myApp.TazkartiApp.Enums.EventType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private LocalDate eventDate;
    private LocalTime clock ;
    private String description;
    private Double price;

    @Enumerated(EnumType.STRING)
    private EventType eventType;
}