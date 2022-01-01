package dev.appkr.emexample;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "singers")
public class Singer {

  @Id
  private Long id;
  private String name;
}
