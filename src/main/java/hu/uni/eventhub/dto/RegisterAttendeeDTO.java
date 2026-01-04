package hu.uni.eventhub.dto;

import jakarta.validation.constraints.NotNull;

public record RegisterAttendeeDTO(
        @NotNull Long attendeeId
) {}
