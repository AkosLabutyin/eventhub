package hu.uni.eventhub.mapper;

import hu.uni.eventhub.dto.AttendeeDTO;
import hu.uni.eventhub.dto.SaveAttendeeDTO;
import hu.uni.eventhub.entity.Attendee;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AttendeeMapper {
    AttendeeDTO toDto(Attendee entity);
    Attendee toEntity(SaveAttendeeDTO dto);

    void updateEntity(SaveAttendeeDTO dto, @MappingTarget Attendee entity);
}
