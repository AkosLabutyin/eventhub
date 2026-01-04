package hu.uni.eventhub.dto;

import java.time.LocalDateTime;

public record EventDTO(
        Long id,
        String name,
        String location,
        LocalDateTime startTime,
        LocalDateTime endTime
) {}
