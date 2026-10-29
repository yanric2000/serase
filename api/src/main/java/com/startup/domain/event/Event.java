package com.startup.domain.event;

import com.startup.domain.IdentifiedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "events")
@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Event extends IdentifiedEntity {

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, length = 4000)
  private String description;

  @Column(nullable = false)
  private String venue;

  @Column(nullable = false)
  private String city;

  @Column(nullable = false)
  private double latitude;

  @Column(nullable = false)
  private double longitude;

  @Column(name = "starts_at", nullable = false)
  private LocalDateTime startsAt;

  public Event(
      String name,
      String description,
      String venue,
      String city,
      double latitude,
      double longitude,
      LocalDateTime startsAt) {
    this.name = name;
    this.description = description;
    this.venue = venue;
    this.city = city;
    this.latitude = latitude;
    this.longitude = longitude;
    this.startsAt = startsAt;
  }
}
