package hu.uni.eventhub.mapper;

import hu.uni.eventhub.dto.EventDTO;
import hu.uni.eventhub.dto.SaveEventDTO;
import hu.uni.eventhub.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventDTO toDto(Event entity);
    Event toEntity(SaveEventDTO dto);

    void updateEntity(SaveEventDTO dto, @MappingTarget Event entity);
}




