package com.startup.domain.saveddrink;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SavedDrinkRequestDTO(@NotBlank String drinkId, @Size(max = 500) String note) {}
