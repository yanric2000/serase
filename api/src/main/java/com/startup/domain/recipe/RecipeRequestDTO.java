package com.startup.domain.recipe;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RecipeRequestDTO(
    @NotBlank String drinkId,
    @NotBlank @Size(max = 255) String title,
    @NotBlank @Size(max = 4000) String ingredients,
    @NotBlank @Size(max = 4000) String steps) {}
