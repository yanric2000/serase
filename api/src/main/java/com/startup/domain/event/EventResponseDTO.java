package com.startup.domain.event;

import java.time.LocalDateTime;

public record EventResponseDTO(
    String id,
    String name,
    String description,
    String venue,
    String city,
    double latitude,
    double longitude,
    LocalDateTime startsAt,
    Double distanceKm) {

  public static EventResponseDTO from(Event event, Double distanceKm) {
    return new EventResponseDTO(
        event.getId(),
        event.getName(),
        event.getDescription(),
        event.getVenue(),
        event.getCity(),
        event.getLatitude(),
        event.getLongitude(),
        event.getStartsAt(),
        distanceKm);
  }
}
