package com.myApp.TazkartiApp.services;

import com.myApp.TazkartiApp.DTO.EventDTO;
import com.myApp.TazkartiApp.model.Event;
import com.myApp.TazkartiApp.Repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public EventDTO createEvent(EventDTO eventDTO) {
        Event event = mapToEntity(eventDTO);
        Event savedEvent = eventRepository.save(event);
        return mapToDTO(savedEvent);
    }

    @Override
    public EventDTO updateEvent(Long id, EventDTO eventDTO) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id " + id));

        if (eventDTO.getName() != null) {
            event.setName(eventDTO.getName());
        }
        if (eventDTO.getLocation() != null) {
            event.setLocation(eventDTO.getLocation());
        }
        if (eventDTO.getEventDate() != null) {
            event.setEventDate(eventDTO.getEventDate());
        }
        if (eventDTO.getDescription() != null) {
            event.setDescription(eventDTO.getDescription());
        }
        if (eventDTO.getPrice() != null) {
            event.setPrice(eventDTO.getPrice());
        }

        Event updatedEvent = eventRepository.save(event);
        return mapToDTO(updatedEvent);
    }

    @Override
    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id " + id));
        eventRepository.delete(event);
    }

    private EventDTO mapToDTO(Event event) {
        return new EventDTO(
                event.getId(),
                event.getName(),
                event.getLocation(),
                event.getEventDate(),
                event.getDescription(),
                event.getPrice()
        );
    }

    private Event mapToEntity(EventDTO dto) {
        return new Event(
                dto.getId(),
                dto.getName(),
                dto.getLocation(),
                dto.getEventDate(),
                dto.getDescription(),
                dto.getPrice()
        );
    }
}
