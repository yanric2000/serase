package com.startup.repositories;

import com.startup.domain.saveddrink.SavedDrink;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavedDrinkRepository extends JpaRepository<SavedDrink, String> {

  @EntityGraph(attributePaths = "drink")
  List<SavedDrink> findByUserId(String userId);

  Optional<SavedDrink> findByUserIdAndDrinkId(String userId, String drinkId);
}
