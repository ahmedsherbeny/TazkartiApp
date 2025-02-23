package com.myApp.TazkartiApp.DTO;

import com.myApp.TazkartiApp.model.Ticket;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Setter
public class TicketCreateDTO extends TicketDTO {
    private Long adminId;

  public   TicketCreateDTO(Long eventId,String seatNumber,long adminId) {
      super();
        this.setEventId(eventId);
        this.setSeatNumber(seatNumber);
        this.setAdminId(adminId);
    }
}

