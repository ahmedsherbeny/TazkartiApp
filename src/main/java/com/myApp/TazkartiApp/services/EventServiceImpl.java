package com.myApp.TazkartiApp.services;

import com.myApp.TazkartiApp.DTO.EventDTO;
import com.myApp.TazkartiApp.model.Event;
import com.myApp.TazkartiApp.Repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EventDTO getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id " + id));
        return mapToDTO(event);
    }

    @Override
    @Transactional
    public EventDTO createEvent(EventDTO eventDTO) {
        Event event = mapToEntity(eventDTO);
       if (eventDTO.getName() == null || eventDTO.getName().isEmpty()) {
          throw new RuntimeException("Event name is required");
       }
        if (eventDTO.getLocation() == null || eventDTO.getLocation().isEmpty()) {
            throw new RuntimeException("Event location is required");
        }
        if (eventDTO.getEventDate() == null) {
            throw new RuntimeException("Event date is required");
        }
        if (eventDTO.getClock() == null) {
            throw new RuntimeException("Event clock is required");
        }
        if (eventDTO.getDescription() == null ) {
            throw new RuntimeException("Event Description Cant be empty");
        }
        if (eventDTO.getPrice() == null || eventDTO.getPrice() <= 0) {
            throw new RuntimeException("Event price must be greater than zero");
        }

        Event savedEvent = eventRepository.save(event);
        return mapToDTO(savedEvent);
    }

    @Override
    @Transactional
    public EventDTO updateEvent(Long id, EventDTO eventDTO) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id " + id));

        if (eventDTO.getName() != null) event.setName(eventDTO.getName());
        if (eventDTO.getLocation() != null) event.setLocation(eventDTO.getLocation());
        if (eventDTO.getEventDate() != null) event.setEventDate(eventDTO.getEventDate());
        if (eventDTO.getClock() != null) event.setClock(eventDTO.getClock());
        if (eventDTO.getDescription() != null) event.setDescription(eventDTO.getDescription());
        if (eventDTO.getPrice() != null) event.setPrice(eventDTO.getPrice());
        if (eventDTO.getEventType() != null) event.setEventType(eventDTO.getEventType());

        Event updatedEvent = eventRepository.save(event);
        return mapToDTO(updatedEvent);
    }


    @Override
    @Transactional
    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id " + id));
        eventRepository.delete(event);
    }

    private EventDTO mapToDTO(Event event) {
        return new EventDTO(event.getId(),event.getName(), event.getLocation(), event.getEventDate(),event.getClock(), event.getDescription(), event.getPrice() , event.getEventType());
    }

    private Event mapToEntity(EventDTO dto) {
        return new Event(dto.getId(), dto.getName(),dto.getLocation(), dto.getEventDate(),dto.getClock(),dto.getDescription(), dto.getPrice(), dto.getEventType());
    }
}
