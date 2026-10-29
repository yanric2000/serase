package com.startup.repositories;

import com.startup.domain.drink.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkRepository extends JpaRepository<Drink, String> {}
