package com.startup.domain.saveddrink;

import com.startup.domain.IdentifiedEntity;
import com.startup.domain.drink.Drink;
import com.startup.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(
    name = "saved_drinks",
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "drink_id"}))
@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SavedDrink extends IdentifiedEntity {

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "drink_id", nullable = false)
  private Drink drink;

  @Column(length = 500)
  private String note;

  public SavedDrink(User user, Drink drink, String note) {
    this.user = user;
    this.drink = drink;
    this.note = note;
  }
}
