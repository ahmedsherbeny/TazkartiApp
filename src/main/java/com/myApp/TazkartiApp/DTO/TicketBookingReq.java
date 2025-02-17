package com.myApp.TazkartiApp.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TicketBookingReq {
    @NotNull
    private String seatNumber;

    @NotNull
    private Long ticketId;
}
