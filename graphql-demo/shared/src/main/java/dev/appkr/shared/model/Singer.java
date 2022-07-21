package dev.appkr.shared.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = {"id"})
public class Singer {

  Long id;
  String name;

  public Singer(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  protected Singer() {
  }
}
