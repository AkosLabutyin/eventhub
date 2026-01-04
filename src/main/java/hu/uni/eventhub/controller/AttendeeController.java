package hu.uni.eventhub.controller;

import hu.uni.eventhub.dto.AttendeeDTO;
import hu.uni.eventhub.dto.SaveAttendeeDTO;
import hu.uni.eventhub.service.AttendeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendees")
@RequiredArgsConstructor
@Tag(name = "Attendees", description = "CRUD operations for attendees")
public class AttendeeController {

    private final AttendeeService attendeeService;

    @GetMapping
    @Operation(summary = "List all attendees")
    public List<AttendeeDTO> listAllAttendees() {
        return attendeeService.listAllAttendees();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Load attendee by id")
    public AttendeeDTO loadAttendee(@PathVariable Long id) {
        return attendeeService.loadAttendee(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create attendee")
    public AttendeeDTO createAttendee(@Valid @RequestBody SaveAttendeeDTO dto) {
        return attendeeService.createAttendee(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update attendee")
    public AttendeeDTO editAttendee(@PathVariable Long id, @Valid @RequestBody SaveAttendeeDTO dto) {
        return attendeeService.updateAttendee(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete attendee")
    public void deleteAttendee(@PathVariable Long id) {
        attendeeService.deleteAttendee(id);
    }

}
