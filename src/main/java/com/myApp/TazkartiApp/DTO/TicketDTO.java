package com.myApp.TazkartiApp.DTO;

import com.myApp.TazkartiApp.Enums.TicketStatus;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class TicketDTO {
    private Long id;
    private String seatNumber;
    private Double price;
    private Long eventId;
    private Long userId;
    private TicketStatus status;

}
