package com.startup.controllers;

import com.startup.domain.drink.DrinkRequestDTO;
import com.startup.domain.drink.DrinkResponseDTO;
import com.startup.services.DrinkService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/drinks")
@RequiredArgsConstructor
public class DrinkController {

  private final DrinkService drinks;

  @PostMapping
  public ResponseEntity<DrinkResponseDTO> create(@RequestBody @Valid DrinkRequestDTO body) {
    return ResponseEntity.status(HttpStatus.CREATED).body(drinks.create(body));
  }

  @GetMapping
  public ResponseEntity<List<DrinkResponseDTO>> list() {
    return ResponseEntity.ok(drinks.list());
  }

  @GetMapping("/{id}")
  public ResponseEntity<DrinkResponseDTO> get(@PathVariable String id) {
    return ResponseEntity.ok(drinks.get(id));
  }
}
