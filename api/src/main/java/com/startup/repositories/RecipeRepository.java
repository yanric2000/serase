package com.startup.repositories;

import com.startup.domain.recipe.Recipe;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, String> {

  @EntityGraph(attributePaths = "drink")
  List<Recipe> findByDrinkId(String drinkId);

  @Override
  @EntityGraph(attributePaths = "drink")
  List<Recipe> findAll();
}
