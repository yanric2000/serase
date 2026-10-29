package com.startup.domain.recipe;

public record RecipeResponseDTO(
    String id, String drinkId, String drinkName, String title, String ingredients, String steps) {

  public static RecipeResponseDTO from(Recipe recipe) {
    return new RecipeResponseDTO(
        recipe.getId(),
        recipe.getDrink().getId(),
        recipe.getDrink().getName(),
        recipe.getTitle(),
        recipe.getIngredients(),
        recipe.getSteps());
  }
}
