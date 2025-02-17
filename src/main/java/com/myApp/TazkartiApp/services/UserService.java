package com.myApp.TazkartiApp.services;

import com.myApp.TazkartiApp.DTO.TicketDTO;
import com.myApp.TazkartiApp.DTO.UserCreateDTO;
import com.myApp.TazkartiApp.DTO.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserCreateDTO createUser(UserCreateDTO userDTO);
    UserCreateDTO updateUser(Long id, UserCreateDTO userDTO);
    void deleteUser(Long id);
    TicketDTO bookTicket(Long userId, Long eventId, String seatNumber,Long ticketId);
}
