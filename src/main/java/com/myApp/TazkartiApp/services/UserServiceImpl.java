package com.myApp.TazkartiApp.services;

import com.myApp.TazkartiApp.DTO.TicketBookingReq;
import com.myApp.TazkartiApp.DTO.TicketDTO;
import com.myApp.TazkartiApp.DTO.UserCreateDTO;
import com.myApp.TazkartiApp.DTO.UserDTO;
import com.myApp.TazkartiApp.Enums.TicketStatus;
import com.myApp.TazkartiApp.Repositories.EventRepository;
import com.myApp.TazkartiApp.Repositories.TicketRepository;
import com.myApp.TazkartiApp.model.Event;
import com.myApp.TazkartiApp.model.Ticket;
import com.myApp.TazkartiApp.model.User;
import com.myApp.TazkartiApp.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapToUserDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return mapToUserDTO(user);
    }

    @Override
    @Transactional
    public UserCreateDTO createUser(UserCreateDTO userDTO) {
        if (userDTO.getUsername() == null || userDTO.getUsername().isEmpty()) {
            throw new RuntimeException("Username is required");
        }
        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            throw new RuntimeException("User Email is required");
        }
        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            throw new RuntimeException("User Password is required");
        }

        User user = mapToEntity(userDTO);
        user = userRepository.save(user);
        return mapToDTO(user);
    }

    @Override
    @Transactional
    public UserCreateDTO updateUser(Long id, UserCreateDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (userDTO.getUsername() != null && !userDTO.getUsername().isEmpty()) {
            user.setUsername(userDTO.getUsername());
        }
        if (userDTO.getEmail() != null && !userDTO.getEmail().isEmpty()) {
            user.setEmail(userDTO.getEmail());
        }
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(userDTO.getPassword());
        }
        if (userDTO.getRole() != null) {
            user.setRole(userDTO.getRole());
        }

        User updatedUser = userRepository.save(user);
        return mapToDTO(updatedUser);
    }


    @Override
    @Transactional
    public void deleteUser(Long id) {userRepository.deleteById(id);}

    @Override
    @Transactional
  public List<TicketDTO> bookTicket(Long userId, Long eventId, Collection<TicketBookingReq> ticketRequests){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        List<TicketDTO> bookedTickets = new ArrayList<>();

        for (TicketBookingReq ticketRequest : ticketRequests) {
            Ticket ticket = ticketRepository.findBySeatNumberAndEventId(ticketRequest.getSeatNumber(),eventId)
                    .orElseThrow(() -> new RuntimeException("Ticket not available with this seat number  " + ticketRequest.getSeatNumber()));

            if (!ticket.getStatus().equals(TicketStatus.AVAILABLE)) {
                throw new RuntimeException("Ticket with id " + ticket.getSeatNumber() + " is already booked");
            }

            ticket.setUser(user);
            ticket.setEvent(event);
            ticket.setSeatNumber(ticketRequest.getSeatNumber());
            ticket.setStatus(TicketStatus.BOOKED);

            Ticket savedTicket = ticketRepository.save(ticket);
            bookedTickets.add(new TicketDTO(
                    savedTicket.getId(),
                    savedTicket.getSeatNumber(),
                    savedTicket.getPrice(),
                    savedTicket.getClock(),
                    userId,
                    eventId,
                    savedTicket.getStatus()
            ));
        }

        return bookedTickets;
    }



    private UserCreateDTO mapToDTO(User user)
    {
        UserCreateDTO userDTO = new UserCreateDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());

        return userDTO;
    }
    private UserDTO mapToUserDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(),user.getTickets(),user.getRole());
    }


    private User mapToEntity(UserCreateDTO userDTO) {
        return new User(null, userDTO.getUsername(), userDTO.getEmail(), userDTO.getPassword(),null, userDTO.getRole());
    }



}
