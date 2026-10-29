package com.startup.domain.event;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record EventRequestDTO(
    @NotBlank @Size(max = 255) String name,
    @NotBlank @Size(max = 4000) String description,
    @NotBlank @Size(max = 255) String venue,
    @NotBlank @Size(max = 255) String city,
    @NotNull @DecimalMin("-90.0") @DecimalMax("90.0") Double latitude,
    @NotNull @DecimalMin("-180.0") @DecimalMax("180.0") Double longitude,
    @NotNull LocalDateTime startsAt) {}
