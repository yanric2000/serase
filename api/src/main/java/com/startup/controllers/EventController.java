package com.startup.controllers;

import com.startup.domain.event.EventRequestDTO;
import com.startup.domain.event.EventResponseDTO;
import com.startup.services.EventService;
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
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

  private final EventService events;

  @PostMapping
  public ResponseEntity<EventResponseDTO> create(@RequestBody @Valid EventRequestDTO body) {
    return ResponseEntity.status(HttpStatus.CREATED).body(events.create(body));
  }

  @GetMapping
  public ResponseEntity<List<EventResponseDTO>> list(
      @RequestParam(required = false) String city,
      @RequestParam(required = false) Double latitude,
      @RequestParam(required = false) Double longitude,
      @RequestParam(required = false) Double radiusKm) {
    return ResponseEntity.ok(events.list(city, latitude, longitude, radiusKm));
  }

  @GetMapping("/{id}")
  public ResponseEntity<EventResponseDTO> get(@PathVariable String id) {
    return ResponseEntity.ok(events.get(id));
  }
}
