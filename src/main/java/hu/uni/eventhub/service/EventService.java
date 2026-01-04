package hu.uni.eventhub.service;

import hu.uni.eventhub.dto.AttendeeDTO;
import hu.uni.eventhub.dto.EventDTO;
import hu.uni.eventhub.dto.RegisterAttendeeDTO;
import hu.uni.eventhub.dto.SaveEventDTO;
import hu.uni.eventhub.entity.Attendee;
import hu.uni.eventhub.entity.Event;
import hu.uni.eventhub.entity.Registration;
import hu.uni.eventhub.exception.NotFoundException;
import hu.uni.eventhub.mapper.AttendeeMapper;
import hu.uni.eventhub.mapper.EventMapper;
import hu.uni.eventhub.repository.AttendeeRepository;
import hu.uni.eventhub.repository.EventRepository;
import hu.uni.eventhub.repository.RegistrationRepository;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final AttendeeRepository attendeeRepository;
    private final RegistrationRepository registrationRepository;

    private final EventMapper eventMapper;
    private final AttendeeMapper attendeeMapper;

    public List<EventDTO> listAllEvents() {
        return eventRepository.findAll().stream().map(eventMapper::toDto).toList();
    }

    public EventDTO loadEvent(Long id) {
        Event e = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event not found: " + id));
        return eventMapper.toDto(e);
    }

    public EventDTO createEvent(SaveEventDTO dto) {
        Event e = eventMapper.toEntity(dto);
        return eventMapper.toDto(eventRepository.save(e));
    }

    @Transactional
    public EventDTO updateEvent(Long id, SaveEventDTO dto) {
        Event e = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event not found: " + id));
        eventMapper.updateEntity(dto, e);
        return eventMapper.toDto(e);
    }

    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new NotFoundException("Event not found: " + id);
        }
        eventRepository.deleteById(id);
    }

    public List<AttendeeDTO> getAttendeesByEvent(Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new NotFoundException("Event not found: " + eventId);
        }
        return registrationRepository.findByEventId(eventId).stream()
                .map(Registration::getAttendee)
                .map(attendeeMapper::toDto)
                .toList();
    }

    @Transactional
    public void addAttendeeToEvent(Long eventId, RegisterAttendeeDTO dto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found: " + eventId));

        Attendee attendee = attendeeRepository.findById(dto.attendeeId())
                .orElseThrow(() -> new NotFoundException("Attendee not found: " + dto.attendeeId()));

        if (registrationRepository.existsByEventIdAndAttendeeId(eventId, dto.attendeeId())) {
            throw new IllegalArgumentException("Attendee already registered for this event.");
        }

        Registration reg = new Registration();
        reg.setEvent(event);
        reg.setAttendee(attendee);
        registrationRepository.save(reg);
    }

    @Transactional
    public void removeAttendeeFromEvent(Long eventId, Long attendeeId) {
        if (!eventRepository.existsById(eventId)) {
            throw new NotFoundException("Event not found: " + eventId);
        }
        if (!attendeeRepository.existsById(attendeeId)) {
            throw new NotFoundException("Attendee not found: " + attendeeId);
        }

        Registration reg = registrationRepository.findByEventIdAndAttendeeId(eventId, attendeeId)
                .orElseThrow(() -> new NotFoundException("Registration not found for event " + eventId + " and attendee " + attendeeId));

        registrationRepository.delete(reg);
    }
}
