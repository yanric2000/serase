package com.startup.domain.recipe;

import com.startup.domain.IdentifiedEntity;
import com.startup.domain.drink.Drink;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "recipes")
@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Recipe extends IdentifiedEntity {

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "drink_id", nullable = false)
  private Drink drink;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false, length = 4000)
  private String ingredients;

  @Column(nullable = false, length = 4000)
  private String steps;

  public Recipe(Drink drink, String title, String ingredients, String steps) {
    this.drink = drink;
    this.title = title;
    this.ingredients = ingredients;
    this.steps = steps;
  }
}
