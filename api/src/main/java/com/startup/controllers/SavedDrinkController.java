package com.startup.controllers;

import com.startup.domain.saveddrink.SavedDrinkRequestDTO;
import com.startup.domain.saveddrink.SavedDrinkResponseDTO;
import com.startup.services.SavedDrinkService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/saved-drinks")
@RequiredArgsConstructor
public class SavedDrinkController {

  private final SavedDrinkService savedDrinks;

  @PostMapping
  public ResponseEntity<SavedDrinkResponseDTO> save(@RequestBody @Valid SavedDrinkRequestDTO body) {
    return ResponseEntity.status(HttpStatus.CREATED).body(savedDrinks.save(body));
  }

  @GetMapping
  public ResponseEntity<List<SavedDrinkResponseDTO>> listMine() {
    return ResponseEntity.ok(savedDrinks.listMine());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    savedDrinks.delete(id);
    return ResponseEntity.noContent().build();
  }
}
