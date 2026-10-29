package com.startup.domain.saveddrink;

public record SavedDrinkResponseDTO(
    String id, String drinkId, String drinkName, String drinkCategory, String note) {

  public static SavedDrinkResponseDTO from(SavedDrink savedDrink) {
    return new SavedDrinkResponseDTO(
        savedDrink.getId(),
        savedDrink.getDrink().getId(),
        savedDrink.getDrink().getName(),
        savedDrink.getDrink().getCategory(),
        savedDrink.getNote());
  }
}
