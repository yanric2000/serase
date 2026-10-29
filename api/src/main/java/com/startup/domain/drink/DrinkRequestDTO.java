package com.startup.domain.drink;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DrinkRequestDTO(
    @NotBlank @Size(max = 255) String name,
    @NotBlank @Size(max = 4000) String description,
    @NotBlank @Size(max = 255) String category) {}
