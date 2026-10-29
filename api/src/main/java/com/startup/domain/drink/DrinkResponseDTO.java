package com.startup.domain.drink;

public record DrinkResponseDTO(String id, String name, String description, String category) {

  public static DrinkResponseDTO from(Drink drink) {
    return new DrinkResponseDTO(drink.getId(), drink.getName(), drink.getDescription(), drink.getCategory());
  }
}
