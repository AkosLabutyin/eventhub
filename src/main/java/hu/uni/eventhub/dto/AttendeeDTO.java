package hu.uni.eventhub.dto;

public record AttendeeDTO(
        Long id,
        String fullName,
        String email,
        String preferredType
) {}
