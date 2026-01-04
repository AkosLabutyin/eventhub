package hu.uni.eventhub.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record SaveEventDTO(
        @NotBlank String name,
        @NotBlank String location,
        @NotNull LocalDateTime startTime,
        @NotNull LocalDateTime endTime
) {}
