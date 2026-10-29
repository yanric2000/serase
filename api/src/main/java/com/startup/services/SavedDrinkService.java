package com.startup.services;

import com.startup.domain.saveddrink.SavedDrink;
import com.startup.domain.saveddrink.SavedDrinkRequestDTO;
import com.startup.domain.saveddrink.SavedDrinkResponseDTO;
import com.startup.infra.exception.ConflictException;
import com.startup.infra.exception.NotFoundException;
import com.startup.repositories.SavedDrinkRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class SavedDrinkService {

  private final SavedDrinkRepository savedDrinks;
  private final DrinkService drinks;
  private final AuthenticatedUser authenticatedUser;

  @Transactional
  public SavedDrinkResponseDTO save(SavedDrinkRequestDTO request) {
    var user = authenticatedUser.get();
    if (savedDrinks.findByUserIdAndDrinkId(user.getId(), request.drinkId()).isPresent()) {
      throw new ConflictException("Essa bebida já está salva");
    }
    var drink = drinks.find(request.drinkId());
    var saved = savedDrinks.save(new SavedDrink(user, drink, request.note()));
    return SavedDrinkResponseDTO.from(saved);
  }

  @Transactional(readOnly = true)
  public List<SavedDrinkResponseDTO> listMine() {
    var user = authenticatedUser.get();
    return savedDrinks.findByUserId(user.getId()).stream().map(SavedDrinkResponseDTO::from).toList();
  }

  @Transactional
  public void delete(String id) {
    var user = authenticatedUser.get();
    var saved = savedDrinks.findById(id).orElseThrow(() -> new NotFoundException("Bebida salva não encontrada"));
    if (!saved.getUser().getId().equals(user.getId())) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você só pode remover as suas bebidas salvas");
    }
    savedDrinks.delete(saved);
  }
}
