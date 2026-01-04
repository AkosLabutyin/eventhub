package hu.uni.eventhub.dto;

import jakarta.validation.constraints.*;

public record SaveAttendeeDTO(
        @NotBlank String fullName,
        @NotBlank @Email String email,
        @NotBlank String preferredType
) {}
