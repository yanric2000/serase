package com.startup.services;

import com.startup.domain.recipe.Recipe;
import com.startup.domain.recipe.RecipeRequestDTO;
import com.startup.domain.recipe.RecipeResponseDTO;
import com.startup.infra.exception.NotFoundException;
import com.startup.repositories.RecipeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecipeService {

  private final RecipeRepository recipes;
  private final DrinkService drinks;

  @Transactional
  public RecipeResponseDTO create(RecipeRequestDTO request) {
    var drink = drinks.find(request.drinkId());
    var recipe = recipes.save(new Recipe(drink, request.title(), request.ingredients(), request.steps()));
    return RecipeResponseDTO.from(recipe);
  }

  @Transactional(readOnly = true)
  public List<RecipeResponseDTO> list(String drinkId) {
    if (drinkId != null && !drinkId.isBlank()) {
      drinks.find(drinkId);
      return recipes.findByDrinkId(drinkId).stream().map(RecipeResponseDTO::from).toList();
    }
    return recipes.findAll().stream().map(RecipeResponseDTO::from).toList();
  }

  @Transactional(readOnly = true)
  public RecipeResponseDTO get(String id) {
    var recipe = recipes.findById(id).orElseThrow(() -> new NotFoundException("Receita não encontrada"));
    return RecipeResponseDTO.from(recipe);
  }
}
