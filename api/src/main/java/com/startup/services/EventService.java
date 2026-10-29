package com.startup.services;

import com.startup.domain.event.Event;
import com.startup.domain.event.EventRequestDTO;
import com.startup.domain.event.EventResponseDTO;
import com.startup.domain.event.GeoDistance;
import com.startup.infra.exception.NotFoundException;
import com.startup.repositories.EventRepository;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class EventService {

  private static final double DEFAULT_RADIUS_KM = 25.0;

  private final EventRepository events;

  @Transactional
  public EventResponseDTO create(EventRequestDTO request) {
    var event =
        events.save(
            new Event(
                request.name(),
                request.description(),
                request.venue(),
                request.city(),
                request.latitude(),
                request.longitude(),
                request.startsAt()));
    return EventResponseDTO.from(event, null);
  }

  @Transactional(readOnly = true)
  public EventResponseDTO get(String id) {
    return EventResponseDTO.from(find(id), null);
  }

  @Transactional(readOnly = true)
  public List<EventResponseDTO> list(String city, Double latitude, Double longitude, Double radiusKm) {
    if ((latitude == null) != (longitude == null)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe latitude e longitude juntas");
    }

    var found =
        (city == null || city.isBlank()) ? events.findAll() : events.findByCityIgnoreCase(city.trim());

    if (latitude != null && longitude != null) {
      var radius = radiusKm == null ? DEFAULT_RADIUS_KM : radiusKm;
      return found.stream()
          .map(event -> new Nearby(event, GeoDistance.kilometers(latitude, longitude, event.getLatitude(), event.getLongitude())))
          .filter(nearby -> nearby.distanceKm() <= radius)
          .sorted(Comparator.comparingDouble(Nearby::distanceKm))
          .map(nearby -> EventResponseDTO.from(nearby.event(), nearby.distanceKm()))
          .toList();
    }

    return found.stream()
        .sorted(Comparator.comparing(Event::getStartsAt))
        .map(event -> EventResponseDTO.from(event, null))
        .toList();
  }

  private Event find(String id) {
    return events.findById(id).orElseThrow(() -> new NotFoundException("Evento não encontrado"));
  }

  private record Nearby(Event event, double distanceKm) {}
}
