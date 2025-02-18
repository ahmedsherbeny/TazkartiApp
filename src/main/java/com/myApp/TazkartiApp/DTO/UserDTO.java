package com.myApp.TazkartiApp.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myApp.TazkartiApp.Enums.UserRole;
import com.myApp.TazkartiApp.model.Ticket;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    @JsonIgnore
    private List<Ticket> tickets ;
    private UserRole role;

}

