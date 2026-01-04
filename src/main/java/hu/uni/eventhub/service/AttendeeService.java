package hu.uni.eventhub.service;

import hu.uni.eventhub.dto.AttendeeDTO;
import hu.uni.eventhub.dto.SaveAttendeeDTO;
import hu.uni.eventhub.entity.Attendee;
import hu.uni.eventhub.exception.ConflictException;
import hu.uni.eventhub.exception.NotFoundException;
import hu.uni.eventhub.mapper.AttendeeMapper;
import hu.uni.eventhub.repository.AttendeeRepository;
import hu.uni.eventhub.repository.RegistrationRepository;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;
    private final AttendeeMapper attendeeMapper;
    private final RegistrationRepository registrationRepository;

    public List<AttendeeDTO> listAllAttendees() {
        return attendeeRepository.findAll().stream().map(attendeeMapper::toDto).toList();
    }

    public AttendeeDTO loadAttendee(Long id) {
        Attendee a = attendeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Attendee not found: " + id));
        return attendeeMapper.toDto(a);
    }

    public AttendeeDTO createAttendee(SaveAttendeeDTO dto) {
        Attendee a = attendeeMapper.toEntity(dto);
        return attendeeMapper.toDto(attendeeRepository.save(a));
    }

    @Transactional
    public AttendeeDTO updateAttendee(Long id, SaveAttendeeDTO dto) {
        Attendee a = attendeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Attendee not found: " + id));
        attendeeMapper.updateEntity(dto, a);
        return attendeeMapper.toDto(a);
    }

    public void deleteAttendee(Long id) {
        if (!attendeeRepository.existsById(id)) {
            throw new NotFoundException("Attendee not found: " + id);
        }
        if (registrationRepository.existsByAttendeeId(id)) {
            throw new ConflictException("Attendee is registered to an event, cannot delete");
        }
        attendeeRepository.deleteById(id);
    }
}
