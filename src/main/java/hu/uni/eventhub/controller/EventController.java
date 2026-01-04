package hu.uni.eventhub.controller;

import hu.uni.eventhub.dto.AttendeeDTO;
import hu.uni.eventhub.dto.EventDTO;
import hu.uni.eventhub.dto.RegisterAttendeeDTO;
import hu.uni.eventhub.dto.SaveEventDTO;
import hu.uni.eventhub.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@Tag(name = "Events", description = "CRUD operations for events and event-attendee registrations")
public class EventController {

    private final EventService eventService;

    @GetMapping
    @Operation(summary = "List all events")
    public List<EventDTO> listAllEvents() {
        return eventService.listAllEvents();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Load event by id")
    public EventDTO loadEvent(@PathVariable Long id) {
        return eventService.loadEvent(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create event")
    public EventDTO createEvent(@Valid @RequestBody SaveEventDTO dto) {
        return eventService.createEvent(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update event")
    public EventDTO editEvent(@PathVariable Long id, @Valid @RequestBody SaveEventDTO dto) {
        return eventService.updateEvent(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete event")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }

    // Relationship endpoints
    @GetMapping("/{eventId}/attendees")
    @Operation(summary = "List attendees registered for an event")
    public List<AttendeeDTO> getAttendeesByEvent(@PathVariable Long eventId) {
        return eventService.getAttendeesByEvent(eventId);
    }

    @PostMapping("/{eventId}/attendees")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Register attendee to event")
    public void addAttendeeToEvent(@PathVariable Long eventId, @Valid @RequestBody RegisterAttendeeDTO dto) {
        eventService.addAttendeeToEvent(eventId, dto);
    }

    @DeleteMapping("/{eventId}/attendees/{attendeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove attendee from event")
    public void removeAttendeeFromEvent(@PathVariable Long eventId, @PathVariable Long attendeeId) {
        eventService.removeAttendeeFromEvent(eventId, attendeeId);
    }
}
