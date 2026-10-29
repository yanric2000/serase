package com.startup.domain.drink;

import com.startup.domain.IdentifiedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "drinks")
@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Drink extends IdentifiedEntity {

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, length = 4000)
  private String description;

  @Column(nullable = false)
  private String category;

  public Drink(String name, String description, String category) {
    this.name = name;
    this.description = description;
    this.category = category;
  }
}
