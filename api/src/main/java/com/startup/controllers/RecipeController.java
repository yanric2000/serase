package com.startup.controllers;

import com.startup.domain.recipe.RecipeRequestDTO;
import com.startup.domain.recipe.RecipeResponseDTO;
import com.startup.services.RecipeService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {

  private final RecipeService recipes;

  @PostMapping
  public ResponseEntity<RecipeResponseDTO> create(@RequestBody @Valid RecipeRequestDTO body) {
    return ResponseEntity.status(HttpStatus.CREATED).body(recipes.create(body));
  }

  @GetMapping
  public ResponseEntity<List<RecipeResponseDTO>> list(@RequestParam(required = false) String drinkId) {
    return ResponseEntity.ok(recipes.list(drinkId));
  }

  @GetMapping("/{id}")
  public ResponseEntity<RecipeResponseDTO> get(@PathVariable String id) {
    return ResponseEntity.ok(recipes.get(id));
  }
}
