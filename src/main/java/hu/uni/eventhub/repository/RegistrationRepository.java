package hu.uni.eventhub.repository;

import hu.uni.eventhub.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findByEventId(Long eventId);
    boolean existsByEventIdAndAttendeeId(Long eventId, Long attendeeId);
    Optional<Registration> findByEventIdAndAttendeeId(Long eventId, Long attendeeId);
    boolean existsByEventId(Long eventId);
    boolean existsByAttendeeId(Long attendeeId);
}