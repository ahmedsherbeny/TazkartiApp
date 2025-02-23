package com.myApp.TazkartiApp.Controllers;

import com.myApp.TazkartiApp.DTO.TicketBookingReq;
import com.myApp.TazkartiApp.DTO.TicketDTO;
import com.myApp.TazkartiApp.DTO.UserCreateDTO;
import com.myApp.TazkartiApp.DTO.UserDTO;
import com.myApp.TazkartiApp.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping
    public ResponseEntity<UserCreateDTO> createUser(@Valid @RequestBody UserCreateDTO userDTO) {
        UserCreateDTO createdUser = userService.createUser(userDTO);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/{userId}/book/{eventId}")
    public ResponseEntity <List<TicketDTO>> bookTicket(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @Valid@RequestBody Collection<TicketBookingReq> ticketBookingReq) {
       List<TicketDTO>  ticket = userService.bookTicket(userId, eventId,ticketBookingReq);
        return ResponseEntity.ok(ticket);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserCreateDTO> updateUser(@PathVariable Long id,@Valid @RequestBody UserCreateDTO userDTO) {
        UserCreateDTO updatedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser( @PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
