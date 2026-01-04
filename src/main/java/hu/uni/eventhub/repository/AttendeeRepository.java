package hu.uni.eventhub.repository;

import hu.uni.eventhub.entity.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendeeRepository extends JpaRepository<Attendee, Long> {
}
