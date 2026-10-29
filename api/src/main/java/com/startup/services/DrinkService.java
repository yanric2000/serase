package com.startup.services;

import com.startup.domain.drink.Drink;
import com.startup.domain.drink.DrinkRequestDTO;
import com.startup.domain.drink.DrinkResponseDTO;
import com.startup.infra.exception.NotFoundException;
import com.startup.repositories.DrinkRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DrinkService {

  private final DrinkRepository drinks;

  @Transactional
  public DrinkResponseDTO create(DrinkRequestDTO request) {
    var drink = drinks.save(new Drink(request.name(), request.description(), request.category()));
    return DrinkResponseDTO.from(drink);
  }

  @Transactional(readOnly = true)
  public List<DrinkResponseDTO> list() {
    return drinks.findAll().stream().map(DrinkResponseDTO::from).toList();
  }

  @Transactional(readOnly = true)
  public DrinkResponseDTO get(String id) {
    return DrinkResponseDTO.from(find(id));
  }

  @Transactional(readOnly = true)
  public Drink find(String id) {
    return drinks.findById(id).orElseThrow(() -> new NotFoundException("Bebida não encontrada"));
  }
}
